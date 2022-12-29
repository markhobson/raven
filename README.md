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

### Actors

Each user exists as an [ActivityPub Actor](https://www.w3.org/TR/activitypub/#actors):

```bash
curl https://social.example/alice
```

They are represented as an [ActivityStreams Actor](https://www.w3.org/TR/activitystreams-core/#actors) using
[JSON-LD](https://www.w3.org/TR/json-ld/) with an [ActivityStream context](https://www.w3.org/ns/activitystreams):

```json
{
  "@context": [
      "https://www.w3.org/ns/activitystreams",
      "https://w3id.org/security/v1"
  ],
  "id": "https://social.example/alice",
  "type": "Person",
  "inbox": "https://social.example/alice/inbox",
  "outbox": "https://social.example/alice/outbox",
  "preferredUsername": "alice",
  "publicKey": {
    "id": "https://social.example/alice#main-key",
    "owner": "https://social.example/alice",
    "publicKeyPem": "-----BEGIN PUBLIC KEY-----\n...\n-----END PUBLIC KEY-----\n"
  }
}
```

The user's public key is also provided via the [Security context](https://web-payments.org/vocabs/security) for
signature verification, as recommended by [Authentication and Authorization best
practices](https://www.w3.org/wiki/SocialCG/ActivityPub/Authentication_Authorization). 

### Activities

A user's published [activities](https://www.w3.org/TR/activitystreams-core/#activities) are available in their actor's
[outbox](https://www.w3.org/TR/activitypub/#outbox):

```bash
curl https://social.example/alice/outbox
```

```json
{
  "@context": "https://www.w3.org/ns/activitystreams",
  "type": "OrderedCollection",
  "totalItems": 1,
  "orderedItems": [
    {
      "type": "Create",
      "actor": "http://social.example/alice",
      "object": {
        "type": "Note",
        "content": "Hello world"
      }
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

Or to run the server locally using Docker on port 8080:

```
docker build -t raven-server .
docker run --rm -p8080:8080 raven-server
```

The latest Docker image is also available on [Docker Hub](https://hub.docker.com/r/markhobson/raven-server):

```
docker run --rm -p8080:8080 markhobson/raven-server
```

# Deploying

To deploy the server to [Fly.io](https://fly.io/):

1. [Install flyctl](https://fly.io/docs/hands-on/install-flyctl/)
2. Create an account with `fly auth signup` or login with `fly auth login`
3. Create an app:
   ```
   fly apps create <app-name>
   ```
4. Deploy the app:
   ```
   fly deploy
   ```
5. Open the app in a browser:
   ```
   fly open
   ```

The server will be running on `https://<app-name>.fly.dev`.

To [configure a custom domain](https://fly.io/docs/app-guides/custom-domains-with-fly/) for the app:

1. Add a CNAME record to your custom domain that points to `<app-name>.fly.dev`
2. Create the TLS certificate:
   ```
   fly certs create <custom-domain>
   ```
3. Wait until the following reports that the certificate has been issued:
   ```
   fly certs show <custom-domain>
   ```

The server will be running on `https://<custom-domain>`.

[![CI](https://github.com/markhobson/raven/actions/workflows/ci.yml/badge.svg)](https://github.com/markhobson/raven/actions/workflows/ci.yml)
