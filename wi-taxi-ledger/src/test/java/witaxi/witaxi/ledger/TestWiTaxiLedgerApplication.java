package witaxi.witaxi.ledger;

import org.springframework.boot.SpringApplication;

public class TestWiTaxiLedgerApplication {

    public static void main(String[] args) {
        SpringApplication.from(WiTaxiLedgerApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
