# Raven

Social media server.

## Implementation

### User discovery

Uses [WebFinger](https://www.rfc-editor.org/rfc/rfc7033) with [acct](https://www.rfc-editor.org/rfc/rfc7565) resources
to discover users.

[![CI](https://github.com/markhobson/raven/actions/workflows/ci.yml/badge.svg)](https://github.com/markhobson/raven/actions/workflows/ci.yml)

## Building

To build the server:

```
mvn clean install
```

To run the server locally on port 8080:

```
mvn spring-boot:run
```
