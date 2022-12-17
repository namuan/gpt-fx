# GptFx

#### Development

You'll need Java17 and JavaFX to build and run this application.
The easiest way is to use [SDKMAN](https://sdkman.io/).

Once you have SDKMAN installed, you can install `17.0.5.fx-zulu` which includes the `JavaFX` library.

```shell
sdk install java 17.0.5.fx-zulu
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

To generate icons from svg

```shell
make icons
```
