variable "do_token" {
	description = "DigitalOcean API token"
	type = string
	sensitive = true
}

variable "ssh_key_name" {
  description = "DigitalOcean SSH key name"
  type = string
}
