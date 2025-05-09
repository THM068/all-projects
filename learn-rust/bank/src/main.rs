mod test;

#[derive(Debug)]
struct Account {
    id: u32,
    holder: String,
    balance: i32,
}

#[derive(Debug)]
struct Bank {
    accounts: Vec<Account>,
}

impl Bank {
    fn new() -> Self {
        Bank {
            accounts: vec![],
        }
    }

    fn add_account(&mut self, account: Account) {
        self.accounts.push(account);
    }

    fn total_balance(&self) -> i32 {
        self.accounts.iter().map(|account| account.balance).sum()
    }

    fn summary(&self) -> Vec<String>  {
    self.accounts.iter()
                  .map(|account| account.summary())
                  .collect::<Vec<String>>()
    }
}
impl Account {
    fn new(id: u32, balance: i32, holder: String) -> Self {
        Account {
            id,
            holder,
            balance: balance,
        }
    }

    fn summary(&self) -> String {
        format!("Account {}: {} has a balance of {}", self.id, self.holder, self.balance)
    }

    fn deposit(&mut self, amount: i32) -> i32 {
        self.balance += amount;
        self.balance
    }

    fn withdraw(&mut self, amount: i32) -> i32 {
        self.balance -= amount;
        self.balance
    }
}
fn main() {
    println!("Hello, world!");
    let account = Account::new(1, 0, "Alice".to_owned());
    let accounts = vec![account];
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn test_bank_new() {
        let bank = Bank::new();
        assert_eq!(bank.accounts.len(), 0);
    }

    #[test]
    fn test_account_new() {
        let account = Account::new(1, 0, "Alice".to_owned());
        assert_eq!(account.id, 1);
        assert_eq!(account.balance, 0);
        assert_eq!(account.holder, "Alice");
    }

    #[test]
    fn test_bank_add_account() {
        let mut bank = Bank::new();
        let account = Account::new(1, 0, "Alice".to_owned());
        bank.add_account(account);
        assert_eq!(bank.accounts.len(), 1);
    }

    #[test]
    fn test_deposit() {
        let amount = 50;
        let mut account = Account::new(1, 100, "Alice".to_owned());
        account.deposit(50);
        assert_eq!(account.balance, 150);
    }

    #[test]
    fn test_account_withdraw() {
        let amount = 50;
        let mut account = Account::new(1, 100, "Alice".to_owned());
        account.withdraw(50);
        assert_eq!(account.balance, 50);
    }

    #[test]
    fn test_account_summary() {
        let account = Account::new(1, 100, "Alice".to_owned());
        assert_eq!(account.summary(), "Account 1: Alice has a balance of 100");
    }

    #[test]
    fn test_bank_acounts_balance() {
        let mut bank = Bank::new();
        let account1 = Account::new(1, 100, "Alice".to_owned());
        let account2 = Account::new(2, 20, "Alex".to_owned());
        let account3 = Account::new(3, 40, "Chris".to_owned());

        bank.add_account(account1);
        bank.add_account(account2);
        bank.add_account(account3);

        assert_eq!(bank.total_balance(), 160);
    }

}
