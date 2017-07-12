TODO list:

(?) unsure if is needed
?: optional/low priority
!: high priority
!!: has other parts depending on its completion

Common Side:
add all Commands that will be moved across communicators!!
add all CommandResults that will be moved across communicators!!
add a game interface for client/server to implement?


Server Side:
add DB w/tables (user and authToken tables)!
clean up DAO classes!
implement common serverfacade.commands!
finalize Model classes!
implement clientproxy functions
    EX: a login command could go from: CC -> SC -> CommandHandler -> ClientProxy -> DAO -> Database and then create and send a result to the CC from that order
figure out what to do with the ServerFacade(?)
add times to authToken(?)


Client Side:
build UI (while following MVC pattern)!
expand on ClientModel class (including game, player models) (the further we get into UI implementation, the clearer the requirements for the ClientModel will become)
create serverfacade.commands and send them where/when needed
implement common serverfacade.commands
add a sort of timeout for AsyncTasks when they take too long to get a response? (stackoverflow makes it seem difficult, most solutions end up making the call on the UI thread and waiting)