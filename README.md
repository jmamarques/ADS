# ADS

Arquitetura e desenho de software liberdade na escolha de ferramentas não é necessario aviso prévio

Projeto:
Trazer dúvidas/ interpretação do problemas sugestões de como vamos fazer visão sobre o problema Estratégia para resolver
o problema

deploy and host - soluções

ter o mapeamento em memoria o carregamento das salas de aulas -> estrutura criterios de qualidade estrategia simples

a nossa app gera mutiplos horarios criterios qualidade -> dos horários

tipos de salas <- atributos criterios de qualidade:
furos numeros de aulas nao alocadas horarios do professor movimentacoes entre edificios

Project 3: Dynamic selection and invocation of scheduling/timetabling algorithms

- Flexible import Reading (from user and from schedule/timetable initial CSV, XML, Json, BD, Web Services data) and
  computing scheduling/timetabling problem properties
- Schedules/timetables problem representation
- Query the knowledge base for the selection of a set of the most suitable scheduling/timetabling algorithms which are
  able to solve a problem with those properties
- Information extraction and visualization of scheduling/timetabling algorithms (matching the set of the most suitable
  scheduling/timetabling algorithms) from a selected (Java/Python) library/API containing a large variety of algorithms
- Taking into account the information extracted from the (Java/Python) library/API, perform the dynamic invocation of
  the most suited algorithm present in the library, according to its availability in the library/API and its order in
  the most suited algorithms selection list
- Compute generated schedules/timetables quality metrics
- Export the generated (best) schedules/timetables (CSV, XML, Json, DB, Web Service)

Cirérios de qualidade Irão ser escolhidos os diferentes algoritmos que melhor cumpram os critérios de qualidade
definidos, porque não irá existir um algoritmo que resolva de forma perfeita a alocação de todas as aulas às salas.​
Definição dos critérios de qualidade:

* O menor número de faltas de alocação de aulas às salas
* O menor número de mudanças de salas em conjuntos de aulas
* O menor número de mudanças de edifícios em conjuntos de aulas
* Maior número de auditórios, com várias horas seguidas, sem alocação de aulas
* O menor número de horas entre aulas
* O utilizador irá poder optar pelo horário que prefere conforme o peso que atribui a cada critério de qualidade.

o que falta fazer é:
FE:
pagina para upload do ficheiro csv, json, excel adição do mapeamento chamada da API Escolher criterios de qualidades
mais relevantes antes da chamada da API (extra)
Apresentar resultados BE:
Completar documentação Docker Criação de testes Implementação dos algoritmos Chamada aos pedidos do projecto 1 criação
do ficheiro final com os horarios error handling
