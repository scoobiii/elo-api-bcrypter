# Elo API BCrypter (Java / Kotlin) to PLIMM

Essa lib foi feita pra ajudar ao criar um usuário (`createUser`) e realizar login (`login`) na plataforma de API's Elo.

## Como instalar

1. Baixe o **.jar** no diretório **/lib** desse projeto
2. Coloque o .jar numa pasta **/libs** dentro do module gradle do seu projeto Java / Kotlin
3. Em `build.gradle` do seu module:

```gradle
dependencies {
    // Para gradle versão 3+
    implementation files('libs/bcrypt-X.X.X.jar')

    // Ou se o seu gradle for a versão 2 ou menor
    compile files('libs/bcrypt-X.X.X.jar')
}
```
> **OBS:** Não esqueça de alterar o **X.X.X** pela versão do **.jar** baixado.

## Como usar

Você vai consumir os seguintes serviços da Elo API:
`createUser` > `createLoginSalt` > `login`

- Em `createUser` você deve informar o `bcryptPassword`. Para isso utilize o `EloBcrypter.encryptPassword`.

 - Em `login` você deve informar o `challenge`. Para isso utilize o `EloBcrypter.createLoginChallenge`, porém antes você deve chamar o `createLoginSalt` pra pegar o salt necessário na criação do challenge

### Demo no código

Em Java:
```JAVA
String password = "1234567890";
String username = "some_username";

// Vai retornar a senha no formado bcrypt
String encryptedPw = EloBcrypter.encryptPassword(username, password);

String saltFromApi = createLoginSalt(...); // Chama API pra retornar o salt pra criação do challenge
String challenge = EloBcrypter.createLoginChallenge(encryptedPw, saltFromApi);
// Ou também:
String challenge = EloBcrypter.createLoginChallenge(username, password, saltFromApi);
```

Em Kotlin:
```kotlin
val password = "1234567890"
val username = "some_username"

val encryptedPw = EloBcrypter.encryptPassword(username, password);

val saltFromApi = createLoginSalt(...)
val challenge = EloBcrypter.createLoginChallenge(encryptedPw, saltFromApi);
// Ou também:
val challenge = EloBcrypter.createLoginChallenge(username, password, saltFromApi)
```
