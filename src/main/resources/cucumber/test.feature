Feature: Lesson for working with files

  Scenario: <translate to English> -> "Прочитать строки из файла и поменять местами первое и последнее слова в каждой строке."
    Given I have file "C:\Users\user\IdeaProjects\Learning\src\main\resources\InputText.txt" with string content
    When I read file content to List
    When I change place of "1" word with "3" word
    Then I write changed list to file "output_file_name"
    Then I print changed list to console