# Task 3 results

## 1. Retrieve deployments names
GET https://ai-proxy.lab.epam.com/openai/deployments
```json
{
    "data": [
        {
            "id": "dall-e-3",
            "model": "dall-e-3",
            "owner": "organization-owner",
            "object": "deployment",
            "status": "succeeded",
            "created_at": 1672534800,
            "updated_at": 1672534800,
            "scale_settings": {
                "scale_type": "standard"
            },
            "features": {
                "rate": false,
                "tokenize": false,
                "truncate_prompt": false,
                "configuration": false,
                "system_prompt": true,
                "tools": false,
                "seed": false,
                "url_attachments": false,
                "folder_attachments": false
            }
        },
        {
            "id": "gpt-35-turbo-0125",
            "model": "gpt-35-turbo-0125",
            "owner": "organization-owner",
            "object": "deployment",
            "status": "succeeded",
            "created_at": 1672534800,
            "updated_at": 1672534800,
            "scale_settings": {
                "scale_type": "standard"
            },
            "features": {
                "rate": false,
                "tokenize": false,
                "truncate_prompt": false,
                "configuration": false,
                "system_prompt": true,
                "tools": false,
                "seed": false,
                "url_attachments": false,
                "folder_attachments": false
            }
        },
        {
            "id": "gpt-35-turbo-0301",
            "model": "gpt-35-turbo-0301",
            "owner": "organization-owner",
            "object": "deployment",
            "status": "succeeded",
            "created_at": 1672534800,
            "updated_at": 1672534800,
            "scale_settings": {
                "scale_type": "standard"
            },
            "features": {
                "rate": false,
                "tokenize": false,
                "truncate_prompt": false,
                "configuration": false,
                "system_prompt": true,
                "tools": false,
                "seed": false,
                "url_attachments": false,
                "folder_attachments": false
            }
        },
        {
            "id": "gpt-35-turbo-0613",
            "model": "gpt-35-turbo-0613",
            "owner": "organization-owner",
            "object": "deployment",
            "status": "succeeded",
            "created_at": 1672534800,
            "updated_at": 1672534800,
            "scale_settings": {
                "scale_type": "standard"
            },
            "features": {
                "rate": false,
                "tokenize": false,
                "truncate_prompt": false,
                "configuration": false,
                "system_prompt": true,
                "tools": false,
                "seed": false,
                "url_attachments": false,
                "folder_attachments": false
            }
        },
        {
            "id": "gpt-35-turbo-1106",
            "model": "gpt-35-turbo-1106",
            "owner": "organization-owner",
            "object": "deployment",
            "status": "succeeded",
            "created_at": 1672534800,
            "updated_at": 1672534800,
            "scale_settings": {
                "scale_type": "standard"
            },
            "features": {
                "rate": false,
                "tokenize": false,
                "truncate_prompt": false,
                "configuration": false,
                "system_prompt": true,
                "tools": false,
                "seed": false,
                "url_attachments": false,
                "folder_attachments": false
            }
        },
      ...
   ]
}
```

## 2. Ensure the application can call different models
Case 1<br>
POST http://localhost:8080/v2/prompt
Request:
```json
{
    "input": "Brainstorm names for a home wifi ssid.",
    "temperature": 0.8,
    "modelName": "gpt-4o",
    "allowKernelFunctions": false
}
```
Response:
```json
{
    "result": "It appears there was some repetition earlier. If you're still looking for creative SSID names for your home Wi-Fi, here are more suggestions to consider:\n\n1. **LAN of Milk and Honey**\n2. **No More Mr. Wi-Fi**\n3. **The C:Hive**\n4. **Wi-Fight the Power**\n5. **Pinging in the Rain**\n6. **Nacho Wi-Fi**\n7. **Connecting...**\n8. **IP Frequently**\n9. **Not Your Wi-Fi**\n10. **Wi Believe We Can Fi**\n\nIf you have any other questions or need further assistance, feel free to ask!"
}
```
Case 2<br>
POST http://localhost:8080/v2/prompt
Request:
```json
{
    "input": "Brainstorm names for a home wifi ssid.",
    "temperature": 0.8,
    "modelName": "gpt-35-turbo-0125",
    "allowKernelFunctions": false
}
```
Response:
```json
{
  "result": "Sure, here are some brainstormed names for a home wifi SSID: \n\n1. NetNirvana\n2. Connectopia\n3. StreamDream\n4. FamilyNetwork\n5. WifiWonderland\n6. CyberSanctuary\n7. TheWifiZone\n8. TechTropolis\n9. SignalStronghold\n10. DigitalDwelling \n\nFeel free to mix and match or modify any of these suggestions to create your own unique SSID!"
}
```

## 3. Comparing different models and settings
Case 1<br>
Request:
```json
{
    "input": "How to make a good coffee?",
    "temperature": 0.2,
    "modelName": "amazon.titan-tg1-large",
    "allowKernelFunctions": false
}
```
Response:
```json
{
  "result": "|\n |\n>>>\nNot at all. What makes you ask that?"
}
```

Case 2<br>
Request:
```json
{
    "input": "How to make a good coffee?",
    "temperature": 0.3,
    "modelName": "gpt-4o",
    "allowKernelFunctions": false
}
```
Response:
```json
{
  "result": "Making a good coffee involves selecting quality ingredients, paying attention to detail, and experimenting with different methods to suit your taste. Here's a basic guide to get you started:\n\n### Ingredients and Equipment\n- **Fresh Coffee Beans:** Start with high-quality, freshly roasted beans. The roast level (light, medium, or dark) depends on your preference.\n- **Grinder:** A burr grinder is ideal for a consistent grind size.\n- **Water:** Use fresh, filtered water. The coffee is mainly water, so water quality matters.\n- **Brewing Equipment:** Such as a French press, drip coffee maker, espresso machine, or pour-over setup (like Chemex or Hario V60).\n- **Scale:** To measure coffee and water accurately.\n\n### Steps to Make a Good Coffee\n\n1. **Measure Your Coffee and Water:**\n   - The general guideline is a ratio of 1:15 to 1:18 coffee to water by weight (e.g., 20 grams of coffee to 300 ml of water).\n\n2. **Grind Your Beans:**\n   - Grind just before brewing. The size of the grind depends on your brewing method:\n     - **Coarse** for French press.\n     - **Medium** for drip coffee makers.\n     - **Fine** for espresso machines.\n     - **Medium-fine** for pour-over.\n\n3. **Heat the Water:**\n   - Aim for a temperature of about 195-205°F (90-96°C).\n\n4. **Brew the Coffee:**\n   - **French Press:** Add ground coffee, pour hot water, stir, and steep for about 4 minutes before pressing.\n   - **Drip Coffee Maker:** Follow the manufacturer's instructions with freshly ground coffee.\n   - **Espresso Machine:** Use fine ground coffee and tamp evenly before brewing the espresso.\n   - **Pour-Over:** Use a circular pouring motion to evenly saturate the grounds and maintain a consistent pour over time.\n\n5. **Serve and Enjoy:**\n   - Pour your fresh coffee into a pre-warmed mug to maintain temperature.\n   - Taste and adjust to personal preferences (milk, cream, sugar, etc., as desired).\n\n### Tips\n- **Experiment:** Finding your perfect cup may require some trial and error with different beans, grind sizes, and brewing times.\n- **Clean Equipment:** Regularly clean your brewing equipment to avoid buildup and ensure the best flavor.\n- **Storage:** Keep coffee beans in an airtight container in a cool, dark place to preserve freshness.\n\nBy following these guidelines and experimenting with different variables, you can enjoy a great cup of coffee tailored to your taste."
}
```

Case 3<br>
Request:
```json
{
    "input": "How to make a good coffee?",
    "temperature": 0.3,
    "modelName": "Mixtral-8x7B-Instruct-v0.1",
    "allowKernelFunctions": false
}
```
Response:
```json
{
  "result": " I'd be happy to help you make a good cup of coffee! Here are some general steps you can follow to make a delicious cup of coffee:\n\n1. Start with high-quality, freshly roasted coffee beans. The fresher the beans, the better the flavor will be.\n2. Grind the beans just before brewing. A burr grinder is the best option, as it will give you a consistent grind size.\n3. Measure the coffee accurately. A good rule of thumb is to use 1 to 2 tablespoons of coffee per six ounces of water, depending on your taste preferences.\n4. Use the right water. The water should be clean and fresh, and ideally, it should be heated to just below boiling (around 200 degrees Fahrenheit).\n5. Choose the right brewing method. Some popular options include drip brewing, French press, pour over, and espresso.\n6. Clean your equipment regularly. Coffee oils and residue can build up over time and affect the flavor of your coffee.\n\nHere are a few additional tips to help you make the best cup of coffee possible:\n\n* Experiment with different coffee beans and roasts to find your favorite.\n* Try using filtered or bottled water if your tap water has a strong taste.\n* Grind the beans to the appropriate size for your brewing method. For example, a French press requires a coarse grind, while espresso requires a fine grind.\n* Don't over-extract the coffee. This can make it taste bitter.\n* Don't under-extract the coffee. This can make it taste sour.\n\nI hope these tips help you make a delicious cup of coffee! Let me know if you have any questions or if there's anything else I can help with."
}
```

Case 4<br>
Request:
```json
{
    "input": "How to make a good coffee?",
    "temperature": 0.6,
    "modelName": "Llama-3-8B-Instruct",
    "allowKernelFunctions": false
}
```
Response:
```json
{
  "result": "Making a good cup of coffee involves several steps, which can be summarized as follows:\n\n1. **Choose High-Quality Coffee Beans**: Fresh, high-quality coffee beans are essential for a good cup of coffee. Look for beans that have been recently roasted and have a good reputation.\n\n2. **Grind the Beans**: Grind the coffee beans just before brewing to ensure the best flavor. The grind size will depend on the brewing method.\n\n3. **Use the Right Water Temperature**: The ideal water temperature for brewing coffee is between 195°F and 205°F.\n\n4. **Use the Right Amount of Coffee**: Use one tablespoon of coffee for every six ounces of water. This is a general rule, but you may need to adjust the amount based on your personal preference.\n\n5. **Choose a Brewing Method**: There are several brewing methods, including:\n   - Drip brewing\n   - French press\n   - Espresso\n   - Pour-over\n   - Chemex\n\n6. **Monitor the Brewing Time**: The brewing time will depend on the brewing method. For example, French press brewing typically takes 4-5 minutes, while pour-over brewing takes 3-4 minutes.\n\n7. **Store the Coffee Correctly**: Store the coffee beans in an airtight container in a cool, dark place to preserve the flavor and aroma.\n\nSome popular brewing methods and their steps are as follows:\n\n### Drip Brewing\n\n1. Place a paper filter in the machine.\n2. Add one tablespoon of coffee for every six ounces of water.\n3. Pour water into the machine.\n4. Wait for the coffee to drip.\n\n### French Press\n\n1. Heat water to the ideal temperature.\n2. Add one tablespoon of coffee for every six ounces of water to the French press.\n3. Pour the heated water over the coffee.\n4. Stir the mixture.\n5. Wait for 4-5 minutes.\n6. Press the plunger to separate the grounds from the liquid.\n\n### Espresso\n\n1. Heat water to the ideal temperature.\n2. Add a small amount of coffee to the espresso machine's portafilter.\n3. Tamp the coffee.\n4. Attach the portafilter to the machine.\n5. Pull a shot of espresso.\n\n### Pour-Over\n\n1. Heat water to the ideal temperature.\n2. Add one tablespoon of coffee for every six ounces of water to the pour-over filter.\n3. Pour a small amount of water over the coffee to saturate the grounds.\n4. Wait for 45 seconds.\n5. Pour the rest of the water over the coffee in a circular motion.\n\n### Chemex\n\n1. Heat water to the ideal temperature.\n2. Add one tablespoon of coffee for every six ounces of water to the Chemex filter.\n3. Pour a small amount of water over the coffee to saturate the grounds.\n4. Wait for 45 seconds.\n5. Pour the rest of the water over the coffee in a circular motion.\n\nBy following these steps and choosing the right brewing method, you can create a perfect cup of coffee that suits your taste preferences."
}
```
Case 5<br>
Request:
```json
{
    "input": "How to make a good coffee?",
    "temperature": 0.6,
    "modelName": "o1-preview-2024-09-12",
    "allowKernelFunctions": false
}
```
Response:
```json
{
  "result": "Making a good cup of coffee is both an art and a science. By paying attention to the quality of ingredients and the brewing process, you can elevate your coffee experience. Here's how to make a great cup of coffee:\n\n### 1. **Select High-Quality Coffee Beans**\n- **Choose Fresh Beans**: Look for beans that have been recently roasted. Freshness significantly impacts flavor.\n- **Select the Right Roast**: Decide between light, medium, or dark roast based on your taste preferences.\n- **Consider Origin and Type**: Different regions produce beans with unique flavor profiles. Experiment with Arabica and Robusta varieties.\n\n### 2. **Store Beans Properly**\n- **Airtight Containers**: Keep beans in a sealed container to preserve freshness.\n- **Cool, Dark Place**: Store away from sunlight, heat, and moisture.\n\n### 3. **Grind Just Before Brewing**\n- **Use a Quality Grinder**: Burr grinders provide a consistent grind size.\n- **Adjust Grind Size**:\n  - **Coarse Grind**: Ideal for French press.\n  - **Medium Grind**: Suitable for drip coffee makers.\n  - **Fine Grind**: Best for espresso machines.\n- **Grind Amount Needed**: Only grind the amount you plan to use immediately.\n\n### 4. **Use Good Water**\n- **Fresh, Filtered Water**: Impurities in water can affect taste.\n- **Proper Water-to-Coffee Ratio**: A general guideline is 1 to 2 tablespoons of ground coffee per 6 ounces (180 milliliters) of water. Adjust to taste.\n\n### 5. **Heat Water to the Right Temperature**\n- **Optimal Brewing Temperature**: Between 195°F and 205°F (90°C to 96°C).\n- **Avoid Boiling Water**: Let boiling water cool for a minute before brewing if you don't have a thermometer.\n\n### 6. **Choose Your Brewing Method**\n\n#### **Drip Coffee Maker**\n- **Prepare the Filter**: Place a paper or permanent filter in the basket.\n- **Add Coffee Grounds**: Measure and add to the filter.\n- **Add Water**: Pour into the reservoir.\n- **Brew**: Start the machine and let it complete the cycle.\n\n#### **French Press**\n- **Add Grounds**: Place coarse grounds into the carafe.\n- **Pour Water**: Add hot water, saturating all the grounds.\n- **Stir**: Give a gentle stir.\n- **Steep**: Let it sit for 4 minutes.\n- **Press**: Slowly push the plunger down to separate grounds from coffee.\n- **Serve Immediately**: To avoid over-extraction, pour coffee into cups or a serving vessel.\n\n#### **Pour-Over**\n- **Prepare Equipment**: Place a filter in the dripper and rinse with hot water.\n- **Add Grounds**: Use medium-fine grounds.\n- **Bloom**: Pour a small amount of water to wet the grounds and wait 30-45 seconds.\n- **Brew**: Slowly pour the remaining water in circular motions over the grounds.\n- **Total Brewing Time**: Aim for about 3-4 minutes.\n\n#### **Espresso**\n- **Prepare Grounds**: Use finely ground coffee.\n- **Tamp Evenly**: Press the coffee evenly into the portafilter.\n- **Brew**: Attach to the machine and extract for about 25-30 seconds.\n- **Yield**: A typical shot is about 1 ounce (30 milliliters).\n\n### 7. **Serve and Customize**\n- **Preheat Your Cup**: Keeps coffee hotter longer.\n- **Additives**: Incorporate milk, cream, sugar, or flavored syrups as desired.\n- **Enjoy Fresh**: Coffee is best enjoyed immediately after brewing.\n\n### 8. **Maintain Your Equipment**\n- **Regular Cleaning**: Prevents buildup of oils and residues.\n- **Descale When Needed**: Remove mineral deposits according to the manufacturer's instructions.\n\n### **Additional Tips**\n- **Experiment**: Don't be afraid to adjust variables like grind size, water temperature, and brewing time to find what you like best.\n- **Use Fresh Milk or Cream**: If adding dairy, ensure it's fresh to enhance the coffee's taste.\n- **Explore Different Beans**: Trying beans from different regions can introduce you to new flavors.\n\nBy focusing on these elements, you can consistently brew delicious coffee that suits your personal taste."
}
```
