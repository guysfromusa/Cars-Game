insert into CAR (id, name, car_type, crashed) values (-1, 'car4', 'MONSTER', false);
insert into MAP (id, name, content, active) values (-1, 'map2', '1,1,1\n0,1,1\n0,0,1', 'ACTIVE');
insert into GAME (id, name, game_status, map_id) values (-1, 'game2', 'RUNNING', -1);
update CAR set game_id = -1 where id = -1;