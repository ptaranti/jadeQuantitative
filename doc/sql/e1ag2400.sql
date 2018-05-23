-- CREATE TABLE behaviourregisterexp1a2400
-- (
--   agent character varying(255),
--   behavior character varying(255),
--   systemtime numeric,
--   simulationtime numeric,
--   error double precision,
--   delay double precision,
--   void character varying(255)
-- )

-- COPY behaviourregisterexp1a2400 FROM '/trabalho/Trabalho/PUC-Rio/Doutorado/Disciplinas/2010.2/Qualificacao/PrimeiroExperimento/Agentes-2400/BehaviourRegister.csv' CSV QUOTE '"' delimiter ';';

SELECT count(error) as total_Amostras, avg(error) AS average_error, stddev(error) AS stddev_error, max(error) AS max_error, avg(delay) AS average_delay, stddev(delay) AS stddev_delay, (min(delay)* (-1)) AS max_delay FROM behaviourregisterexp1a2400 where (simulationtime > 65000);

