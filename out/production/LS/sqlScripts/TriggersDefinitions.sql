
/* saves price to price history, trigger function */
CREATE FUNCTION save_price_history() RETURNS TRIGGER
  LANGUAGE plpgsql AS $$
    BEGIN
      INSERT INTO price_history (price_id, timestamp, price)
      VALUES (NEW.price_id, current_timestamp, NEW.buyout_price);
      RETURN NEW;
    END;
  $$;

CREATE TRIGGER save_history_insert_trigger
AFTER INSERT ON prices FOR EACH ROW
EXECUTE PROCEDURE save_price_history();

CREATE TRIGGER save_history_update_trigger
AFTER UPDATE ON prices FOR EACH ROW
EXECUTE PROCEDURE save_price_history();


DROP TRIGGER save_history_update_trigger ON prices;
DROP TRIGGER save_history_insert_trigger ON prices;