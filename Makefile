MVN    ?= mvn
GRADLE ?= ./gradlew
TEST   ?=

.PHONY: help
help: ## Show this help
	@echo "jmbus - dual Maven/Gradle build. Usage: make <target>"
	@echo
	@grep -E '^[a-zA-Z0-9_-]+:.*## ' $(MAKEFILE_LIST) | sort | \
		awk 'BEGIN{FS=":.*## "} {printf "  \033[36m%-22s\033[0m %s\n", $$1, $$2}'

# --- build ---------------------------------------------------------------

.PHONY: build build-maven build-gradle
build: build-maven build-gradle ## Build + test with both Maven and Gradle

build-maven: ## Build + test with Maven only
	$(MVN) clean install

build-gradle: ## Build + test with Gradle only
	$(GRADLE) clean build

# --- test ------------------------------------------------------------------

.PHONY: test test-maven test-gradle test-one-maven test-one-gradle
test: test-maven test-gradle ## Run tests with both Maven and Gradle (no full rebuild)

test-maven: ## Run tests with Maven only
	$(MVN) test

test-gradle: ## Run tests with Gradle only
	$(GRADLE) test

test-one-maven: ## Run a single Maven test, e.g. make test-one-maven TEST=MBusServiceTest#decodeMessage
	$(MVN) test -Dtest=$(TEST)

test-one-gradle: ## Run a single Gradle test, e.g. make test-one-gradle TEST="*.MBusServiceTest.decodeMessage"
	$(GRADLE) test --tests "$(TEST)"

# --- local install ---------------------------------------------------------

.PHONY: install install-maven install-gradle
install: install-maven install-gradle ## Install the jar to the local Maven repo (~/.m2) via both tools

install-maven: ## Install to ~/.m2 via Maven
	$(MVN) clean install

install-gradle: ## Install to ~/.m2 via Gradle
	$(GRADLE) publishToMavenLocal

# --- release / publish (Maven Central) --------------------------------------
# See PUBLISHING.md for the one-time account/GPG/token setup these assume.

.PHONY: release-jars publish-maven publish-gradle
release-jars: ## Build source+javadoc jars locally (no publish) - sanity-checks the release profile
	$(MVN) clean package -Prelease -DskipTests

publish-maven: ## Publish to Maven Central via Maven (uploads a pending deployment; needs manual "Publish" on the portal)
	$(MVN) clean deploy -Prelease

publish-gradle: ## Publish to Maven Central via Gradle (uploads a pending deployment; needs manual "Publish" on the portal)
	$(GRADLE) publishToMavenCentral

# --- housekeeping ------------------------------------------------------------

.PHONY: clean clean-maven clean-gradle verify
clean: clean-maven clean-gradle ## Remove build output from both tools

clean-maven: ## Remove Maven's target/
	$(MVN) clean

clean-gradle: ## Remove Gradle's build/
	$(GRADLE) clean

verify: clean build ## Full pre-PR check: clean + build + test with both tools (see CONTRIBUTING.md)
