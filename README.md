# BBCNews

This project uses the NewsAPI to retrieve information about top headlines. To utilize the API, you need to obtain a personal API key.

## How to Get an API Key

1. Visit [https://newsapi.org/docs/endpoints/top-headlines](https://newsapi.org/docs/endpoints/top-headlines) for more information about the API.

2. Create an account or log in if you already have one.

3. After logging in, navigate to the API Keys section and create a new key.

4. Copy the generated key.

## Setting Up the API Key in the Project

1. Open the project in a code editor.

2. In the project directory, find the `api.properties` file.

3. Open the `api.properties` file and add the following line, replacing `"YOUR_API_KEY"` with the key you copied earlier:

   ```properties
   API_KEY="YOUR_API_KEY"
