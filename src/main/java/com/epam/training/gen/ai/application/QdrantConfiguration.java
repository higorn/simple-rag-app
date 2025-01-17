package com.epam.training.gen.ai.application;

import io.qdrant.client.QdrantClient;
import io.qdrant.client.QdrantGrpcClient;
import io.qdrant.client.grpc.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutionException;

@Slf4j
@Configuration
public class QdrantConfiguration {
    public static final String COLLECTION_NAME = "text_collection";
    @Bean
    public QdrantClient qdrantClient() throws ExecutionException, InterruptedException {
        var qdrantClient = new QdrantClient(QdrantGrpcClient.newBuilder("localhost", 6334, false)
                .build());
        if (!qdrantClient.collectionExistsAsync(COLLECTION_NAME).get()) {
            var result = qdrantClient.createCollectionAsync(COLLECTION_NAME,
                            Collections.VectorParams.newBuilder()
                                    .setDistance(Collections.Distance.Cosine)
                                    .setSize(1536)
                                    .build())
                    .get();
            log.info("Collection was created: [{}]", result.getResult());
        }
        return qdrantClient;
    }
}
