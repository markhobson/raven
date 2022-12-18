# Raven

Social media server.

## Implementation

### User discovery

Uses [WebFinger](https://www.rfc-editor.org/rfc/rfc7033) with [acct](https://www.rfc-editor.org/rfc/rfc7565) resources
to discover users:

```bash
curl https://social.example/.well-known/webfinger?resource=acct:alice@social.example
```

The server responds with a [JSON Resource Descriptor](https://www.rfc-editor.org/rfc/rfc6415) that links to the user's
actor:

```json
{
  "subject": "acct:alice@social.example",
  "links": [
    {
      "rel": "self",
      "type": "application/activity+json",
      "href": "https://social.example/alice"
    }
  ]
}
```

## Building

To build the server:

```
mvn clean install
```

To run the server locally on port 8080:

```
mvn spring-boot:run
```

Or to run the server locally using Docker on port 80:

```
docker build -t raven-server .
docker run --rm -p80:80 raven-server
```

[![CI](https://github.com/markhobson/raven/actions/workflows/ci.yml/badge.svg)](https://github.com/markhobson/raven/actions/workflows/ci.yml)
