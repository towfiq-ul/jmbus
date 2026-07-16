# Publishing jmbus to Maven Central

This project is configured to publish `io.github.towfiq-ul:jmbus` to Maven Central via the
[Sonatype Central Portal](https://central.sonatype.com), using either build tool:

- **Maven** — `org.sonatype.central:central-publishing-maven-plugin`, gated behind the `release` profile in `pom.xml`.
- **Gradle** — the [`com.vanniktech.maven.publish`](https://vanniktech.github.io/gradle-maven-publish-plugin/) plugin in `build.gradle`.

Neither is wired into the default build — `mvn install`/`./gradlew build` never touch signing or Central credentials.
Publishing is a one-time-setup, then per-release, manual action.

## One-time setup

1. **Create a Central Portal account and verify the namespace.**
   Sign in to https://central.sonatype.com with the **`towfiq-ul` GitHub account** (OAuth login). This
   automatically verifies the `io.github.towfiq-ul` namespace — no manual review needed, because it's tied
   to that exact GitHub account. (This is why the groupId is `io.github.towfiq-ul` and not
   `com.github.towfiq-ul` — Central only auto-verifies the `io.github.<username>` form.)

2. **Generate a User Token** (Central Portal → Account → Generate User Token). This gives you a
   username/password pair — **not** your login password — used by both build tools to authenticate uploads.

3. **Generate a GPG key pair** (Central requires every artifact to be signed):
   ```
   gpg --gen-key
   gpg --keyserver keyserver.ubuntu.com --send-keys <KEY_ID>
   ```
   Publish the public key to a keyserver so Central can verify signatures.

4. **Configure credentials locally** — never commit any of this.

   **Maven** — add to `~/.m2/settings.xml`:
   ```xml
   <settings>
     <servers>
       <server>
         <id>central</id>
         <username>YOUR_PORTAL_TOKEN_USERNAME</username>
         <password>YOUR_PORTAL_TOKEN_PASSWORD</password>
       </server>
     </servers>
     <profiles>
       <profile>
         <id>gpg</id>
         <properties>
           <gpg.keyname>YOUR_KEY_ID</gpg.keyname>
           <gpg.passphrase>YOUR_KEY_PASSPHRASE</gpg.passphrase>
         </properties>
       </profile>
     </profiles>
     <activeProfiles>
       <activeProfile>gpg</activeProfile>
     </activeProfiles>
   </settings>
   ```

   **Gradle** — add to `~/.gradle/gradle.properties` (or the `ORG_GRADLE_PROJECT_*` env var equivalents,
   preferred in CI):
   ```properties
   mavenCentralUsername=YOUR_PORTAL_TOKEN_USERNAME
   mavenCentralPassword=YOUR_PORTAL_TOKEN_PASSWORD
   signing.keyId=YOUR_KEY_ID
   signing.password=YOUR_KEY_PASSPHRASE
   signing.secretKeyRingFile=/home/you/.gnupg/secring.gpg
   ```
   (Or use in-memory keys: `signingInMemoryKey`, `signingInMemoryKeyId`, `signingInMemoryKeyPassword` —
   see the [plugin docs](https://vanniktech.github.io/gradle-maven-publish-plugin/central/).)

## Releasing a new version

Central rejects re-publishing an existing version — bump `<version>` in `pom.xml` **and**
`build.gradle` (they're independent build files and must be kept in sync) before every release.

**Maven:**
```
mvn clean deploy -Prelease
```
This uploads a signed, validated bundle to Central as a *pending* deployment (`autoPublish=false`). Log
into https://central.sonatype.com to review and click "Publish" to make it live.

**Gradle:**
```
./gradlew publishToMavenCentral
```
Also uploads as pending, requiring the same manual "Publish" click on the portal. Use
`./gradlew publishAndReleaseToMavenCentral` instead if you want it to go live immediately without the
manual review step.

Either path is independent — you don't need to run both for a given release, just whichever build tool
you're using at the time.
