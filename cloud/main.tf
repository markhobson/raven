terraform {
	required_providers {
		digitalocean = {
			source = "digitalocean/digitalocean"
			version = "~> 2.0"
		}
	}
}

provider "digitalocean" {
	token = var.do_token
}

resource "digitalocean_droplet" "raven-server" {
	image = "ubuntu-22-04-x64"
	name = "raven-server"
	region = "lon1"
	size = "s-1vcpu-1gb"
}
