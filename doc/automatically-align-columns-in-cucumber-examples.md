
Cucumber project refer to: simple-bdd-automation


Windows:

Menu: code -> Reformat Code
Ctrl+ALT+L


cucumber run edit config:
before "-ea" to add other Environment variables like:
-Dspring.profiles.active=dev -Dtoken="eyaa..." -ea

-----------------------------------------------------------------
Feature: (Feature Name) the version can be retrieved
Scenario Outline: (Scenario Name) client makes call to GET /version outline
Given (Precondition) xxxx
When (Action) the client calls /version with <num>
Then (Verify) the client receives status code of <code>
And (Verify) the client receives server version <version>
Examples: (Parameters)
| num | code | version    |
| 1   | 200  | Result = 1 |
| 0   | 200  | / by zero  |

---------------------------
Principles:
1) 独立性
2) 可重复性
3) 前提条件，行为，预期和结果比对


