package tester;
import java.time.LocalDate;
import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Vector;

import static utils.BankingValidationRules.*;
import com.banking.AcType;
import com.banking.BankAccount;

import static utils.BankingUtils.populateAccountMap;

public class TestBanking {

	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			// init
			// get the popualated map : from utils
			Map<Integer, BankAccount> accountMap = new HashMap<Integer, BankAccount>();
			boolean exit = false;
			while (!exit) {
				System.out.println("Options 1. Display all accts 2. Add Bank Account 3. Disable Account 4. Deposite Amount 5. Withdraw Amount 6. Transfer Amount 7. Sort As Per AccNo 8. Sort by Date And Name 9. Exit");
				try {
					switch (sc.nextInt()) {
					case 1:
						System.out.println("All accounts :");
						for(BankAccount a : accountMap.values())
							System.out.println(a);
						break;
					case 2:
						System.out.println("Enter int acctNo, String customerName, AcType acctType, double balance, LocalDate creationDate");
						int accNo=sc.nextInt();
						BankAccount bk=validateAll(accNo, sc.next(), sc.next(), sc.nextDouble(), sc.next(),accountMap);
						accountMap.put(accNo, bk);
						System.out.println("Account Added successfully!!!");
						break;
					case 3:
						disableAccounts(accountMap);
						break;
					case 4:
						System.out.println("Enter AccountNumber Amount");
						depositeAmount(sc.nextInt(), sc.nextDouble(), accountMap);
						System.out.println("Amount deposited successfuly");
						break;
					case 5:
						System.out.println("Enter AccountNumber Amount");
						withdrawAmount(sc.nextInt(), sc.nextDouble(), accountMap);
						System.out.println("Money Withdrawn Successfuly");
						break;
					case 6:
						System.out.println("Enter AccNumberTransferFrom AccNumberTransferTo Amount");
						transferAmount(sc.nextInt(), sc.nextInt(), sc.nextDouble(), accountMap);
						System.out.println("Money Transfer successful");
						break;
					case 7:
						TreeMap<Integer, BankAccount> tm=new TreeMap<>(accountMap);
						System.out.println("Sorted Accounts ");
						for(BankAccount bank:tm.values()) {
							System.out.println(bank);
						}
						break;
					case 8:
						Vector<BankAccount> accVector=new Vector<>(accountMap.values());
						Collections.sort(accVector, new Comparator<BankAccount>() {
							public int compare(BankAccount a1,BankAccount a2) {
								if(((LocalDate)a1.getCreationDate()).compareTo(a2.getCreationDate())==0) {
									return a1.getCustomerName().compareTo(a2.getCustomerName());
								}
								return ((LocalDate)a1.getCreationDate()).compareTo(a2.getCreationDate());
							}
						});
						break;
					case 9:
						removeAccounts(accountMap);
						System.out.println("Account removed");
						break;
					case 10:
						exit=true;
						break;
						
					}
				} catch (Exception e) {
					sc.nextLine();
					System.out.println(e);
				}
			}
		}

	}

}
