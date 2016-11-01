# ActionAPI
The ActionAPI contains a spigot and bungeecord implementation to fetch and execute Actions from the ActionAPIService.

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
###JOIN:
Connects target to specified server
###CHAT:
Sends array of messages to target chat
###KICK:
Kicks target from network (disconnect)
###BATCH: *Executes an array of actions on the target*

##Spigot Actions
###HUD:
Adds HUDText to specific target HUD location
###BATCH:
Executes an array of actions on the target

##Subject
Actions may contain a field 's', indicating the subject of the action. Defaults to NONE. The action will be executed on the subject.
###ALL
The action will be executed for every player all specified destinations.
###NONE (DEFAULT)
The action will be executed once for every specified destination. The action execution will specify a *null* uuid value as the receiver.
###{uuid}
The action will only execute for the specified *uuid*.
##Destination
An action can have a destination parameter, to specify where to schedule the action too
###*EMPTY*
When the destionation is left empty, it will be set according to the receiver (ALL/NONE = every server, {uuid} = server of that player)
###ALL
This will send the action to every server (of that action type). Don't combine this with a {uuid} receiver (routes a lot of traffic for no reason).
###{UUID}
This will send the action only to the server of the specified player.
