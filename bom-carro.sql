CREATE TABLE Marca (
	ID SERIAL PRIMARY KEY,
	Nome VARCHAR(100) UNIQUE,
	Pais_Origem VARCHAR(100)
);

CREATE TABLE Veiculo (
	ID SERIAL PRIMARY KEY,
	ID_Marca INTEGER,
	Nome_Modelo VARCHAR(100),
	Cor VARCHAR(25),
	Ano_Fabricacao INTEGER,
	Quilometragem_Rodada NUMERIC(8, 2),
	Valor NUMERIC(10, 2),
	FOREIGN KEY (ID_Marca) REFERENCES Marca(ID)
);

INSERT INTO Marca (Nome, Pais_Origem)
VALUES
	('Volkswagen', 'Alemanha'),
	('Fiat', 'Itália'),
	('Chevrolet', 'Estados Unidos'),
	('Renault', 'França'),
	('Ford', 'Estados Unidos'),
	('Toyota', 'Japão'),
	('Hyundai', 'Coreia do Sul'),
	('Honda', 'Japão'),
	('Jeep', 'Estados Unidos'),
	('Nissan', 'Japão'),
	('Citroën', 'França'),
	('Peugeot', 'França'),
	('Mitsubishi', 'Japão'),
	('Mercedes-Benz', 'Alemanha'),
	('BMW', 'Alemanha');

INSERT INTO
	Veiculo (ID_Marca, Nome_Modelo, Cor, Ano_Fabricacao, Quilometragem_Rodada, Valor)
VALUES
	(1, 'Gol', 'Prata', 2018, 60000, 35000),
	(2, 'Uno', 'Branco', 2015, 80000, 25000),
	(3, 'Prisma', 'Preto', 2019, 50000, 45000),
	(4, 'Sandero', 'Vermelho', 2017, 70000, 40000),
	(5, 'Ka', 'Azul', 2020, 30000, 40000),
	(6, 'Corolla', 'Cinza', 2016, 90000, 70000),
	(7, 'HB20', 'Branco', 2019, 40000, 45000),
	(8, 'Fit', 'Prata', 2018, 50000, 50000),
	(9, 'Renegade', 'Preto', 2020, 30000, 80000),
	(10, 'Versa', 'Vermelho', 2017, 70000, 40000),
	(11, 'C3', 'Azul', 2016, 80000, 35000),
	(12, '208', 'Cinza', 2019, 40000, 45000),
	(13, 'Lancer', 'Preto', 2014, 90000, 50000),
	(14, 'Classe A', 'Branco', 2018, 30000, 95000),
	(15, 'Série 3', 'Preto', 2017, 40000, 100000),
	(1, 'Voyage', 'Prata', 2015, 80000, 35000),
	(2, 'Siena', 'Branco', 2016, 70000, 40000),
	(3, 'Cobalt', 'Preto', 2017, 60000, 45000),
	(4, 'Logan', 'Vermelho', 2018, 50000, 40000),
	(5, 'Fusion', 'Azul', 2016, 90000, 80000),
	(6, 'Camry', 'Cinza', 2017, 80000, 90000),
	(7, 'Azera', 'Branco', 2016, 70000, 85000),
	(8, 'Accord', 'Prata', 2018, 60000, 90000),
	(9, 'Cherokee', 'Preto', 2017, 50000, 100000),
	(10, 'Sentra', 'Vermelho', 2016, 80000, 70000),
	(11, 'C4 Pallas', 'Azul', 2015, 90000, 35000),
	(12, '408', 'Cinza', 2016, 80000, 45000),
	(13, 'ASX', 'Preto', 2017, 70000, 50000),
	(14, 'Classe C', 'Branco', 2018, 60000, 95000),
	(15, 'Série 5', 'Preto', 2017, 50000, 100000),
	(1, 'Fox', 'Branco', 2016, 70000, 35000),
	(2, 'Doblo', 'Prata', 2015, 80000, 40000),
	(3, 'Spin', 'Preto', 2017, 60000, 45000),
	(4, 'Captur', 'Vermelho', 2018, 50000, 40000),
	(5, 'Focus', 'Azul', 2016, 90000, 80000),
	(6, 'Yaris', 'Cinza', 2017, 80000, 90000),
	(7, 'Elantra', 'Branco', 2016, 70000, 85000),
	(8, 'City', 'Prata', 2018, 60000, 90000),
	(9, 'Wrangler', 'Preto', 2017, 50000, 100000),
	(10, 'Frontier', 'Vermelho', 2016, 80000, 70000),
	(11, 'C4 Lounge', 'Azul', 2015, 90000, 35000),
	(12, '3008', 'Cinza', 2016, 80000, 45000),
	(13, 'Pajero', 'Preto', 2017, 70000, 50000),
	(14, 'Classe GLA', 'Branco', 2018, 60000, 95000),
	(15, 'X5', 'Preto', 2017, 50000, 100000);

DELETE FROM Marca WHERE ID > 15;
DELETE FROM Veiculo WHERE ID > 45;

SELECT * FROM Marca;
SELECT * From Veiculo;

SELECT	m.Pais_Origem,
		m.Nome,
		v.Nome_Modelo,
		v.Cor,
		v.Ano_Fabricacao,
		v.Quilometragem_Rodada,
		v.Valor
FROM	Veiculo v
JOIN	Marca m
  ON	v.Id_Marca = m.Id;