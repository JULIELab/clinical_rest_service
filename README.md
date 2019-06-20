# clinical_rest_service

UIMA based REST service for finding entities in German clinical documents.

## Capabilities:
* Provide POST endpoint to process plaintext document and identify entities
* Response is sent as JSON stand-off annotations (entity type, start, end, covered text)
* Accepts multiple input encodings (specified via *'Accept-Charset'* header), offset in JSON relative to original encoding
* Also provides GET endpoint to list supported encodings

## Architecture:
* REST endpoints provided by sparkjava framework
* Currently uses embedded Jetty
* Uses UIMA pipeline to apply NLP components
* Only external dependency are models / config file, no need for database etc.

## Java packages
* *restservice* package contains classes for starting/running REST server + *EncodingUtils* for encoding conversion
* *annotation* package contains UIMA wrapping and configuration classes
* *pipelines* package contains wrappers for UIMA pipelines as a preparation for possible multi-threading
