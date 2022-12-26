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

The latest Docker image is also available on [Docker Hub](https://hub.docker.com/r/markhobson/raven-server):

```
docker run --rm -p80:80 markhobson/raven-server
```

# Deploying

To deploy the server to [DigitalOcean](https://www.digitalocean.com/):

1. [Create a DigitalOcean API token](https://docs.digitalocean.com/reference/api/create-personal-access-token/)
2. Create a new file `cloud/terraform.tfvars` and paste the token in as follows:
   ```
   do_token = "<api-token>"
   ```
3. Run [Terraform](https://www.terraform.io/) to provision the infrastructure:
   ```
   cd cloud
   terraform init
   terraform apply
   ```

[![CI](https://github.com/markhobson/raven/actions/workflows/ci.yml/badge.svg)](https://github.com/markhobson/raven/actions/workflows/ci.yml)
