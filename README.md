# GptFx

A simple application to chat with OpenAI's GPT-3 API.
It resends the history of the conversation to the API to maintain the context.

![Demo](docs/images/gptfx-demo.gif)

#### Development

You'll need Java17 and JavaFX to build and run this application.
The easiest way is to use [SDKMAN](https://sdkman.io/).

Once you have SDKMAN installed, you can install `17.0.5-tem`.

```shell
sdk install java 17.0.5-tem
```

Once it is installed, just run the following command to build the application.

```shell
make run 
```  

You can just run `make` to display list of available commands.

```shell
make
```

### Packaging

```shell
make install
```

### Icons

Requires Inkscape and ImageMagick.

```shell
brew install imagemagick
brew install cask inkscape
```

Run the following command to generate icons for all platforms.

```shell
make icons
```
