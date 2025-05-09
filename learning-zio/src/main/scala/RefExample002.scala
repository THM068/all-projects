import RefExample002.Money
import zio.{Clock, Console, Ref, ZIO, ZIOAppDefault, durationInt}

case class Account(balance: Money)
object RefExample002 extends ZIOAppDefault {
  type Money = Double



  def withDraw(account: Ref[Account], amount: Money): ZIO[Any, Nothing, Money] =
    account.modify { account =>
      if(account.balance >= amount) {
        val newBalance = account.balance - amount
        (amount, Account(newBalance))
      } else {
        (0.0, account)
      }
    }

  def bluePrint = for {
    account <- Ref.make(Account(100.0))
    withdrawal1 <- withDraw(account, 70.0)
    withdrawal2 <- withDraw(account, 50.0)
    balance <- account.get
    _ <- Console.printLine(s"First withdrawal: $withdrawal1")
    _ <- Console.printLine(s"Second withdrawal: $withdrawal2")
    _ <- Console.printLine(s"Final balance: ${balance.balance}")
  } yield ()

  val run = bluePrint

  def printTimeForever: ZIO[Any, Throwable, Nothing] =
    Clock.currentDateTime.flatMap(Console.printLine(_)) *>
      ZIO.sleep(1.seconds) *> printTimeForever

}
