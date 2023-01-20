# **FX_IDE**
개인(토이) 프로젝트<br>

## **FX_IDE?**
<b>Java</b> 언어에 한하여 사용자가 입력한 코드를 컴파일 후 결과를 출력해주는 JavaFX Application 입니다.<br>

## **<b>사용기술</b>**
<span><img src="https://img.shields.io/badge/JAVA-007396?style=for-the-badge&logo=JAVA&logoColor=White">
<img src="https://img.shields.io/badge/richtextfx-007396?style=for-the-badge&logo=richtext&logoColor=white">
<img src="https://img.shields.io/badge/css3-1572B6?style=for-the-badge&logo=CSS3&logoColor=white">
<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white">
<img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
<img src="https://img.shields.io/badge/Intellij IDEA-000000?style=for-the-badge&logo=IntelliJ Idea&logoColor=white"></span>

- Client - Java 11, JavaFX 0.0.10, CSS3, FXML, RichTextFx 0.11.0
- Version Control - Git, Github
- IDE - IntelliJ IDEA Ultimate

## [서비스 주요 기능]
- <b>Java Code Compile</b><br>
1. 사용자가 입력한 코드를 .java 파일로 변환
2. Java ProcessBuilder 를 이용하여 명령프로세스(cmd)를 통해 변환된 .java 파일 실행
3. 실행 결과를 Stream 으로 추출 후 컬렉션(ArrayList)에 추가 후 JavaFX Client에 출력
- <b>Java Code Pretty Printing</b>
1. RichTextFx의 CodeArea와 Java Pattern / Matcher를 활용하여 키워드 Code HighLighting
