package com.epam.training.gen.ai.application;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.azure.ai.openai.models.EmbeddingItem;
import com.azure.ai.openai.models.Embeddings;
import com.azure.ai.openai.models.EmbeddingsOptions;
import com.epam.training.gen.ai.controller.dto.Book;
import com.epam.training.gen.ai.controller.dto.BookCreationResponse;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.grpc.JsonWithInt;
import io.qdrant.client.grpc.Points;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static com.epam.training.gen.ai.application.QdrantConfiguration.COLLECTION_NAME;
import static io.qdrant.client.PointIdFactory.id;
import static io.qdrant.client.ValueFactory.value;
import static io.qdrant.client.VectorsFactory.vectors;
import static io.qdrant.client.WithPayloadSelectorFactory.enable;

@Slf4j
@Service
@RequiredArgsConstructor
public class BooksFacade {
    private final OpenAIAsyncClient aiAsyncClient;
    private final QdrantClient qdrantClient;

    public BookCreationResponse create(Book request) {
        var id = UUID.randomUUID();
        var embeddings = getEmbeddings(request.description());
        var points = embeddings.stream().map(EmbeddingItem::getEmbedding).toList();
        var pointStructs = points.stream().map(p -> getPointStruct(p, id, buildPayload(request))).toList();
        saveVector(pointStructs);
        return new BookCreationResponse(id.toString(), embeddings.get(0).getEmbedding());
    }

    private List<EmbeddingItem> getEmbeddings(String text) {
        var embeddings = retrieveEmbeddings(text);
        return embeddings.block().getData();
    }

    private Mono<Embeddings> retrieveEmbeddings(String text) {
        var embeddingsOptions = new EmbeddingsOptions(List.of(text));
        return aiAsyncClient.getEmbeddings("text-embedding-ada-002", embeddingsOptions);
    }

    private Map<String, JsonWithInt.Value> buildPayload(Book request) {
        return Map.of(
                "name", value(request.name()),
                "description", value(request.description()),
                "author", value(request.author()),
                "year", value(request.year()));
    }

    private Points.PointStruct getPointStruct(List<Float> point, UUID id, Map<String, JsonWithInt.Value> payload) {
        return Points.PointStruct.newBuilder()
                .setId(id(id))
                .setVectors(vectors(point))
                .putAllPayload(payload)
                .build();
    }

    private void saveVector(List<Points.PointStruct> pointStructs) {
        Points.UpdateResult updateResult = null;
        try {
            updateResult = qdrantClient.upsertAsync(COLLECTION_NAME, pointStructs).get();
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        log.info(updateResult.getStatus().name());
    }

    public List<Book> search(String term, int limit) {
        var embeddings = retrieveEmbeddings(term);
        var qe = new ArrayList<Float>();

        embeddings.block().getData().forEach(embeddingItem ->
                qe.addAll(embeddingItem.getEmbedding())
        );
        try {
            var result = qdrantClient
                    .searchAsync(
                            Points.SearchPoints.newBuilder()
                                    .setCollectionName(COLLECTION_NAME)
                                    .addAllVector(qe)
                                    .setWithPayload(enable(true))
                                    .setLimit(limit)
                                    .build())
                    .get();
            return getBooks(result);
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }

    }

    private static List<Book> getBooks(List<Points.ScoredPoint> result) {
        return result.stream().map(point -> {
            var payload = point.getPayloadMap();
            var name = payload.get("name").getStringValue();
            var description = payload.get("description").getStringValue();
            var author = payload.get("author").getStringValue();
            var year = payload.get("year").getIntegerValue();
            return new Book(name, description, author, (int) year);
        }).toList();
    }
}
