# ActionAPI
The ActionAPI contains a spigot and bungeecord implementation to fetch and execute Actions from the ActionAPIService.

ActionAPI messages will be consumed up to one time by every destination. When an action is posted, it is registered to all appropriate destinations This means when the destination is specific to the player, it first fetches the server of the player, and publishes the action to that server. So if the player leaves before that servers polls it's messages, he will not receive the message.

##Action format
```json
{  "s":"ffcc67a2-b114-4825-beea-63c4bdee2b21",
   "a":"CHAT",
   "m":{  
      "lines":["Line one", "Line two"]
   }
}
```
- "s": The subject (optional, defaults to NONE), usually the player.
- "d": The destination (optional, defaults according to the subject), indicates what servers this message should schedule too.
- "m": The action metadata, different for every action.

##Bungeecord Actions
###JOIN
Connects target to specified server
###CHAT
Sends array of messages to target chat
###KICK
Kicks target from network (disconnect)
###BATCH
Executes an array of actions on the target

##Spigot Actions
###HUD
Adds HUDText to specific target HUD location
###BATCH
Executes an array of actions on the target

##Subject
Actions may contain a field 's', indicating the subject of the action. Defaults to NONE. The action will be executed on the subject.
- **'ALL'**: Executes for all players on specified destinations *(Useful for broadcasts)*
- **'NONE' [DEFAULT]**:The action executes once for every specified destination *(Useful for in-world events)*
- **'{uuid}'**: Only executes for specified player *(Useful for sending a private msg)*

##Destination
An action can have a destination parameter, to specify where to schedule the action too
- **SUBJECT** [DEFAULT]: Depends on the subject: ALL/NONE: Every server, {uuid}: Server of the uuid player
- **ALL**: Every server receives the action *(useful for global broadcasts)*
- **{uuid}**: The action is send to the server of the specified uuid *(useful for broadcasting on 1 server)*
