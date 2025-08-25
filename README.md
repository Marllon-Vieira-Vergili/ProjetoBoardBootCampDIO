# Projeto de board para gerenciamento de tarefas

@Author: Marllon Vieira Vergili

GITHUB: https://github.com/Marllon-Vieira-Vergili/ProjetoBoardBootCampDIO
Linkedin: https://www.linkedin.com/in/marllon-vieira-vergili-00553a208/

Escreva um código que irá criar um board customizável para acompanhamento de tarefas

## Requisitos
    1 - O código deve iniciar disponibilizando um menu com as seguintes opções: Criar novo board, Selecionar board, Excluir boards, Sair;
    2 - O código deve salvar o board com suas informações no banco de dados MySQL;

## Regras dos boards
    1 - Um board deve ter um nome e ser composto por pelo menos 3 colunas ( coluna onde o card é colocado inicialmente, coluna para cards com tarefas concluídas e coluna para cards cancelados, a nomenclatura das colunas é de escolha livre);
    2 - As colunas tem seu respectivo nome, ordem que aparece no board e seu tipo (Inicial, cancelamento, final e pendente);
    3 - Cada board só pode ter 1 coluna do tipo inicial, cancelamento e final, colunas do tipo pendente podem ter quantas forem necessárias, obrigatoriamente a coluna inicial deve ser a primeira coluna do board, a final deve ser a penúltima e a de cancelamento deve ser a última
    4 - As colunas podem ter 0 ou N cards, cada card tem o seu título, descrição, data de criação e se está bloqueado;
    5 - Um card deve navegar nas colunas seguindo a ordem delas no board, sem pular nenhuma etapa, exceto pela coluna de cards cancelados que pode receber cards diretamente de qualquer coluna que não for a coluna final;
    6 - Se um card estiver marcado como bloqueado ele não pode ser movido até ser desbloqueado
    7 - Para bloquear um card deve-se informar o motivo de seu bloqueio e para desbloquea-lo deve-se também informar o motivo

## Menu de manipulação de board selecionado
    1 - O menu deve permitir mover o card para próxima coluna, cancelar um card, criar um card, bloquea-lo, desbloquea-lo e fechar board;

## Requisitos opcionais
    1 - Um card deve armazenar a data e hora em que foi colocado em uma coluna e a data e hora que foi movido pra a próxima coluna;
    2 - O código deve gerar um relatório do board selecionado com o tempo que cada tarefa demorou para ser concluída com informações do tempo que levou em cada coluna
    3 - O código dever gerar um relatório do board selecionado com o os bloqueios dos cards, com o tempo que ficaram bloqueados e com a justificativa dos bloqueios e desbloqueios.



===========================================
COMO TESTAR:

1 - Faça um git clone no repositório
2- Rode na sua IDE de preferência. (Recomendo Intellij)
3 - Crie um banco de dados no Mysql, para teste. Recomendo deixar o nome: "boardGerTarefasDio" com o comando: "create database boardGerTarefasDio;"
4 - Vá no arquivo application.properties, e altere o datasource.username & datasource.password de acordo com o usuário e senha que vocÊ definiu no seu mysql
5 - Verifique se você está utilizando a versão 21 do JDK.
6 - Vá no arquivo POM.xml e verifique se todas as dependências deste projeto estão baixadas para funcionar este repositório sem erros.
7 - Compilar, ver se não irá dar nenhum erro relacionado a configuração do properties, ou do banco, ou dependência ausente.
8 - Testar o sistema, e adicionar novas funcionalidades (se quiser).
