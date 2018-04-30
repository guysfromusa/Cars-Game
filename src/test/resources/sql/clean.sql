update CAR set game_id = null;
update GAME set map_id = null;
update MOVEMENT_HISTORY set game_id = null;
update MOVEMENT_HISTORY set car_id = null;

delete from CAR;
delete from MAP;
delete from GAME;
delete from MOVEMENT_HISTORY;