INSERT INTO DEVICE(name, type) VALUES('device 1', 'windows');
INSERT INTO DEVICE(name, type) VALUES('device 2', 'windows');
INSERT INTO DEVICE(name, type) VALUES('device 3', 'mac');

INSERT INTO SERVICE(type, device_id) VALUES('antivirus', 1);
INSERT INTO SERVICE(type, device_id) VALUES('backup', 1);
INSERT INTO SERVICE(type, device_id) VALUES('psa', 1);
INSERT INTO SERVICE(type, device_id) VALUES('screen-share', 1);

INSERT INTO SERVICE(type, device_id) VALUES('antivirus', 2);
INSERT INTO SERVICE(type, device_id) VALUES('backup', 2);
INSERT INTO SERVICE(type, device_id) VALUES('psa', 2);

INSERT INTO SERVICE(type, device_id) VALUES('antivirus', 3);
INSERT INTO SERVICE(type, device_id) VALUES('backup', 3);
INSERT INTO SERVICE(type, device_id) VALUES('psa', 3);
INSERT INTO SERVICE(type, device_id) VALUES('screen-share', 3);