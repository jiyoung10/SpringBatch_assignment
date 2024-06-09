# SpringBatch_assignment
Study about springBatch
</br></br>
<h3>■ What is Batch application?</h3>
트리거 등에 의해 서버가 실행될 시 자동으로 대량의 데이터를 처리하는 애플리케이션이다.
</br></br>
<h3>■ Spring Batch Architecture</h3>
*맨 처음 step -> start 로 용어 수정 
</br>
<img src=https://github.com/jiyoung10/SpringBatch_assignment/assets/124184748/ffcfa2dd-e638-49a0-9ca3-8c9925a93afd></img></br></br>
<img src=https://github.com/jiyoung10/SpringBatch_assignment/assets/124184748/ffab931c-34fa-4ee8-bfd3-e9dd56b68f9a></img></br></br>

<h3>*프로젝트 구현 시 발생한 문제점 해결</h3>
1. @builder 사용시 @AllArgsConstructor @NoArgsConstructor 같이 사용하는 것이 좋음.</br>
=> ORM(Object-Relational Mapping) 프레임워크는 엔티티 클래스를 리플렉션을 통해 인스턴스화합니다. 이러한 프레임워크는 기본 생성자가 필요합니다. 기본 생성자가 없으면 JPA는 엔티티를 생성할 수 없어 예외를 발생시킵니다.</br>
참고 : https://good-option-28a.notion.site/Spring-Batch-Assignment-24ed8d9bfdb54d43a94ad1ff315cf735?pvs=4
</br></br>
2. spring boot 3.xx 버전부터는 @EnableBatch가 deprecated 됨.</br>
참고 : https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.0-Migration-Guide#spring-batch-changes
</br></br>
3. @value 어노테이션은 lombok 제공과 spring 프레임워크 제공 두가지가 있음.</br>
=> 외부 프로퍼티를 읽을 때는 spring 프레임워크 제공 어노테이션을 사용해야 함.
