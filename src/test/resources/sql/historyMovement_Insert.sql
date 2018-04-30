insert into CAR (id, name, car_type, crashed) values (-1, 'FIAT', 'MONSTER', false);
insert into CAR (id, name, car_type, crashed) values (-2, 'MERCEDES', 'MONSTER', false);
insert into MAP (id, name, content, active) values (-1, 'map2', '0,1,0\n0,1,1\n0,0,1', 'ACTIVE');
insert into GAME (id, name, game_status, map_id) values (-1, 'game2', 'RUNNING', -1);
insert into GAME (id, name, game_status, map_id) values (-2, 'game3', 'RUNNING', -1);
insert into MOVEMENT_HISTORY(id, GAME_ID, CAR_ID, NEW_POSITION_X, NEW_POSITION_Y, NEW_DIRECTION) VALUES (-1, -1, -1, 0 ,1, 'SOUTH');
insert into MOVEMENT_HISTORY(id, GAME_ID, CAR_ID, NEW_POSITION_X, NEW_POSITION_Y, NEW_DIRECTION) VALUES (-2, -2, -2, 0 ,1, 'NORTH');