-- fixed states for challenges
INSERT INTO state VALUES (1, 'active') ON CONFLICT DO NOTHING;
INSERT INTO state VALUES (2, 'completed') ON CONFLICT DO NOTHING;
INSERT INTO state VALUES (3, 'canceld') ON CONFLICT DO NOTHING;
INSERT INTO state VALUES (4, 'planned') ON CONFLICT DO NOTHING;       
