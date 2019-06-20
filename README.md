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
* Only external dependency are model files, no need for database etc.

## Java packages
* *restservice* contains classes for starting/running REST server + *EncodingUtils* for encoding conversion
* *annotation* contains classes configuring / wrapping a UIMA pipeline
* *annotation.pipelines* contains wrappers for UIMA pipelines as a preparation for possible multi-threading
