/* Optimalizacia frekventovanych selectov podla stlpcov, ktore su pouzivane najviac v joinoch a where podmienke*/

CREATE INDEX medicamentIdIndex ON medicaments(medicament_id);
CREATE INDEX medicamentTitleIndex ON medicaments(title);

CREATE INDEX medicamentCategoryIdIndex ON medicaments_categories(medicament_category_id);
CREATE INDEX medicamentCategoryTitleIndex ON medicaments_categories(title);

CREATE INDEX saleCategoryIdIndex ON sale_categories(sale_category_id);
CREATE INDEX saleCategoryTitleIndex ON sale_categories(title);

CREATE INDEX priceIdIndex ON prices(price_id);

CREATE INDEX medicamentInformationIdIndex ON medicament_information(medicament_information_id);

CREATE INDEX stateIdIndex ON states(state_id);
CREATE INDEX stateTitleIndex ON states(title);


/* Demonstracia indexu */

DROP INDEX medicamentTitleIndex;

EXPLAIN ANALYZE SELECT medicament_id FROM medicaments WHERE title ='fwbfe';