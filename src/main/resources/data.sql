INSERT INTO DEVICE(id, name, type) VALUES(1, 'device 1', 'windows');
INSERT INTO DEVICE(id, name, type) VALUES(2, 'device 2', 'windows');
INSERT INTO DEVICE(id, name, type) VALUES(3, 'device 3', 'mac');

INSERT INTO SERVICE(id, type, device_id) VALUES(1, 'antivirus', 1);
INSERT INTO SERVICE(id, type, device_id) VALUES(2, 'backup', 1);
INSERT INTO SERVICE(id, type, device_id) VALUES(3, 'psa', 1);
INSERT INTO SERVICE(id, type, device_id) VALUES(4, 'screen-share', 1);

INSERT INTO SERVICE(id, type, device_id) VALUES(5, 'antivirus', 2);
INSERT INTO SERVICE(id, type, device_id) VALUES(6, 'backup', 2);
INSERT INTO SERVICE(id, type, device_id) VALUES(7, 'psa', 2);

INSERT INTO SERVICE(id, type, device_id) VALUES(8, 'antivirus', 3);
INSERT INTO SERVICE(id, type, device_id) VALUES(9, 'backup', 3);
INSERT INTO SERVICE(id, type, device_id) VALUES(10, 'psa', 3);
INSERT INTO SERVICE(id, type, device_id) VALUES(11, 'screen-share', 3);