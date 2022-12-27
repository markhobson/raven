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

data "digitalocean_ssh_key" "root" {
	name = var.ssh_key_name
}

resource "digitalocean_droplet" "raven-server" {
	image = "ubuntu-22-04-x64"
	name = "raven-server"
	region = "lon1"
	size = "s-1vcpu-1gb"
	ssh_keys = [data.digitalocean_ssh_key.root.id]
	user_data = file("cloud-init.sh")
}
