# DonateKeys

DonateKeys is a Minecraft plugin that allows users to activate a privilege using an activation key. The plugin works with the PermissionsEX plugin. 

## Commands
- `/activate <key>` - Activates a privilege using an activation key.

## Configuration
The plugin uses a MySQL database to store keys. Here is an example of the `config.yml` file:

```yaml
#DonateKeys Config File#

#=====MySQL connection=====#
mysql:
  hostname: 127.0.0.1
  username: root
  password: "test"
  database: "keys"
  table: "keys"
  port: 3306
  options: "?useSSL=false"

#=====Plugin Messages=====#
plugin:
  errormsg: "§c§l DonateKeys §8| §fUnable to activate key!"
  successmsg: "§c§l DonateKeys §8| §fKey activated successfully!"
  noargmsg: "§c§l DonateKeys §8| §c§lYou did not enter a key!"
  sqlhackmsg: "§c§l DonateKeys §8| §c§lAttempted SQL injection detected!"
```

