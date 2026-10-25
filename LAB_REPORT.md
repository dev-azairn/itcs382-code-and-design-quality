# Lab Report, Code Quality and Design Quality

Repository URL:

## Tools

| Item | Version or name |
| --- | --- |
| JDK | 26.0.2.1 |
| Maven | Apache Maven 3.10.0 |
| IDE | Visual Studio Code 1.142 |
| SonarQube for IDE | 6.1.1 |
| AI tool and exact model | GitHub Copilot  |

## Task 1. SonarQube on the starter code

Commit of the starter, before any change:
`0aed65d (HEAD -> main, origin/main) first commit`
Table 1. SonarQube issues in the starter code

| No. | Rule | File:line | Message |
| --- | --- | --- | --- |
| 1 | java:S106 | Main.java:9 | Replace this use of System.out by a logger. |
| 2 | java:S106 | Main.java:15 | Replace this use of System.out by a logger. |
| 3 | java:S106 | Main.java:17 | Replace this use of System.out by a logger. |
| 4 | java:S106 | Main.java:22 | Replace this use of System.out by a logger. |
| 5 | java:S2143 | ParcelService.java:1 | Use the "java.time" API for date and time. |
| 6 | java:S1128 | ParcelService.java:6 | Remove this unused import 'java.util.Date'. |
| 7 | java:S1068 | ParcelService.java:12 | Remove this unused "maxParcels" private field. |
| 8 | java:S106 | ParcelService.java:30 | Replace this use of System.out by a logger. |
| 9 | java:S4973 | ParcelService.java:42 | Strings and Boxed types should be compared using "equals()". |
| 10 | java:S106 | ParcelService.java:67 | Replace this use of System.out by a logger. |
| 11 | java:S106 | ParcelService.java:69 | Replace this use of System.out by a logger. |
| 12 | java:S2095 | ParcelService.java:76 | Use try-with-resources or close this "FileWriter" in a "finally" clause. |
| 13 | java:S108 | ParcelService.java:80 | Remove this block of code, fill it in, or add a comment explaining why it is empty. |

## Task 2. AI review of the starter code

| Item | Value |
| --- | --- |
| Date | 25 Oct, 2026 |
| AI tool and exact model | GitHub Copilot |
| Commit reviewed | 0aed65da734fe0c81f4bf7fa68567406b5aab4e4 |
| How the AI read the files (attached the files, opened the folder, or pasted the files) | attached the files to GitHub Copilot |
| Output file in the reviews folder | parcelpint-review.md |

Table 2. All findings on the starter code

| No. | File:line | Finding | Found by (SonarQube, AI or both) | SonarQube rule or course term | Correct? (yes or no, and why) |
| --- | --- | --- | --- | --- | --- |
| 1 | ParcelService.java:42 | String comparison should use .equals(), but the code use ==.  | Both | java:S4973 | yes, for the == of string will compare its address, leading to fail on the different string address even the sentences in string are the same. |
| 2 | ParcelService.java:76 | FileWriter is never closed. | Both | java:S2095 | yes, the close() command is not used, may causing resource leak. |
| 3 | ParcelService.java:80 | catch block is empty | Both | java:S108 | yes, there is no logging why it is error. |
| 4 | Main.java:11 | An empty input line yields an empty command, and commands with missing arguments can cause array-index errors in the later branches. | AI | Code Quality | yes, in the case of user input handling, there might be a bug that no word is in the console, causing the runtime error. |
| 5 | Main.java:13 | Nonnumeric or out-of-range size input can throw an exception or create a parcel with a size outside the documented 1–3 range. | AI | Code Quality | yes, if the user type non-number value, the program can throw an error as well as the index-of-bound if user does not give the third value. |
| 6 | ParcelService.java:6 | Date is an unused import. | Both | java:S2143 | yes, it is unused import, avoid naming conflict and enhancing code readability. |
| 7 | ParcelService.java:12 | maxParcels is unused, so it does not enforce a parcel limit. | Both | java:S1128 | yes, yes, it is able to delete for reducing unused line. |
| 8 | Main.java:6 | main handles input, command parsing, multiple command branches, service calls, and output in one method, making the flow harder to read and extend. | AI | Code quality | Not reallt, in my opinion, the code is acceptable for it is on main and pretty well for using less line of code in each if-else block. However, the AI commend will be a "should do" stuff if the code line of if-block is more. |
| 9 | Main.java:9 | Replace this use of `System.out` by a logger. | SonarQube | java:S106 | No, the purpose of the code that is not logging. It tells the instruction to the user. |
| 10 | Main.java:15 | Replace this use of `System.out` by a logger. | SonarQube | java:S106 | No, the purpose of the code that is not logging. It tells the result to the user via console. |
| 11 | Main.java:17 | Replace this use of `System.out` by a logger. | SonarQube | java:S106 | No, the purpose of the code that is not logging. It tells the result to the user via console. |
| 12 | Main.java:22 | Replace this use of `System.out` by a logger. | SonarQube | java:S106 | No, the purpose of the code that is not logging. It tells the result to the user via console. |
| 13 | ParcelService.java:1 | Use the `java.time` API for date and time. | SonarQube | java:S2143 | Yes, it is code recommendation for newer date API to improve the program memory management |
| 14 | ParcelService.java:30 | Replace this use of `System.out` by a logger. | SonarQube | java:S106 | No, the purpose of the code that is not logging. It tells the result to the user via console. |
| 15 | ParcelService.java:67 | Replace this use of `System.out` by a logger. | SonarQube | java:S106 | No, the purpose of the code that is not logging. It tells the result to the user via console. |
| 16 | ParcelService.java:69 | Replace this use of `System.out` by a logger. | SonarQube | java:S106 | No, the purpose of the code that is not logging. It tells the result to the user via console. |
| 17 | Main.java:6 | `main` groups different command operations selected by command text, so it has logical cohesion. | AI | Logical cohesion | Yes, the program changes its function depended on user input. |
| 18 | ParcelService.java:64 | `printParcels` selects between two listing behaviors using `forStaff`, so it has logical cohesion. | AI | Logical cohesion | Yes, it contains logical statement.  |
| 19 | ParcelService.java:9 | `ParcelService` combines locker allocation, parcel lifecycle, fee calculation, notification, console output, listing, and file persistence; `fee` does not use the service's attributes. | AI | Temporal cohesion | Yes, it contains the list of Lockers, Parcels which indicates the Temporal Cohesion. Also, fee avoid using of internal class object which may be a vague design. |
| 20 | Main.java:14 | `Main` depends on `ParcelService`, `Parcel`, and `Locker`, and reaches through `service.getLockers()` to inspect locker state at line 22. | AI | Stamp Coupling | Yes, the main program using a partial functionality from object. |
| 21 | Main.java:19 | The `forStaff` boolean controls which listing behavior the service performs, creating control coupling between `Main` and `ParcelService`. | AI | Control coupling | Yes, it shows the different functionality depended on user input. |
| 22 | Main.java:14 | `Main` receives and inspects whole `Locker` objects returned by the service, creating stamp coupling. | AI | Stamp coupling | Yes, it creates the entire object to use some methods of the Parcel Object. |
| 23 | ParcelService.java:25 | `ParcelService` directly uses locker state and operations, creating stamp coupling with `Locker`. | AI | Stamp coupling | Yes, ParcelService uses the Locker object and calls its methods to check and change the locker. |
| 24 | Locker.java:6 | `Locker` stores and exposes a whole `Parcel` object, creating stamp coupling. | AI | Stamp coupling | Yes, Locker stores the whole Parcel object even though the locker only needs to track which parcel is inside. |
| 25 | ParcelService.java:52 | `fee` accepts a whole `Parcel` but reads only its size, creating stamp coupling. | AI | Stamp coupling | Yes, the fee method only uses the parcel size, so it does not need the whole Parcel object. |
| 26 | ParcelService.java:28 | `ParcelService` directly reads and writes `Parcel.status`, creating content coupling. | AI | Content coupling | Yes, ParcelService changes the public status field directly instead of asking Parcel to update its own status. |
| 27 | Parcel.java:13 | Parcel states are shared between classes through string values such as `"STORED"`, creating connascence of convention. | AI | Connascence of convention | Yes, the classes need to use the exact same status text, so a spelling mistake can cause the status check to fail. |
| 28 | Locker.java:5 | Locker and parcel sizes use numeric codes 1–3 across classes, creating connascence of convention. | AI | Connascence of convention | Yes, the classes depend on the same numbers meaning the same sizes, so using a different number can give the wrong result. |
| 29 | Parcel.java:9 | The meaning of the adjacent `String` constructor arguments depends on their position (`code`, then `studentId`), creating connascence of position. | AI | Connascence of position | Yes, both arguments are String, so switching their order can assign the parcel code as the student ID. |
| 30 | Parcel.java:7 | `status` is a non-private field. | AI | Non-private field | Yes, because other classes can change the status directly without using Parcel's methods. |
| 31 | ParcelService.java:20 | `getLockers()` returns the service's internal mutable collection, so callers can change it. | AI | Exposes internal collection | Yes, the caller receives the actual locker list and can add or remove lockers from ParcelService. |

2.1 Name two problems that only the AI review found.
AI review is able to indicate cohesion-coupling of the code and the error on runtime.

2.2 Name one problem that only SonarQube found, or write none.
The SonarQube detects the logging statement suggenstion. The System.out should check to the default logs. However, the problem is the purpose of system uses does not about logging. It is the console app which will be used via console. So, the log default print pattern does not need for this situation.

2.3 Name one AI finding that was wrong or too vague, and explain why. If every finding was right, explain how you checked them.
Mostly, AI will give the case in the runtime issues and code suggestion. It is great for debugging and mostly was right. I checked the review by looking the code line and analyse following the case. If it makes sense, I let it passed, otherwise I will research about the term it uses, then check it again.


## Task 3. Fix and check

Table 3. What you did with each SonarQube issue in Table 1

| No. in Table 1 | Fixed or kept | Commit, or the reason for keeping it |
| --- | --- | --- |
| 1 |  | |

Number of SonarQube issues that remain after your fixes:

The two design problems that you fixed

| Design problem (course term) | What you changed | Commit |
| --- | --- | --- |
| | | |
| | | |

Output of the program after your fixes, for the commands `store P1 6501 2`, `pickup P1 5` and `staff`, and the content of parcels.csv:

```text

```

## Task 4. AI-generated poor and good designs

| Item | bikeshare-poor | bikeshare-good |
| --- | --- | --- |
| Date | | |
| AI tool and exact model | | |
| Prompt (B or C as given, or your changed prompt in full) | | |
| Builds with mvn package (yes or no) | | |
| Number of classes, interfaces, enums and records | | |
| SonarQube issues, total | | |
| SonarQube issues, by rule | | |
| AI review findings in Check 1 | | |
| AI review findings in Check 2 | | |
| Review output file in the reviews folder | | |

4.1 Did SonarQube report any cohesion or coupling problem in bikeshare-poor? Quote the rule and the message if it did.

4.2 Did the AI review tell the two versions apart? Give one finding for bikeshare-poor that does not appear for bikeshare-good.

4.3 Which version would you rather extend with a new member type, such as a visitor who pays 4 baht per minute, and why?

## Task 5. Reflection

5.1 What does SonarQube check, and what does it not check? Answer with examples from this lab.

5.2 When should you not trust the AI review? Give an example from this lab.

5.3 What will you do differently in Project Phase 2 after this lab?

## AI usage declaration

| AI tool and exact model | Purpose | Files or parts that it produced or changed | How you checked its output |
| --- | --- | --- | --- |
| | | | |
