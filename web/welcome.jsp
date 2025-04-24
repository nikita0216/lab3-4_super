<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:h="http://xmlns.jcp.org/jsf/html"
      xmlns:p="http://primefaces.org/ui">
<h:head>
    <title>Добро пожаловать</title>
</h:head>
<h:body>
    <h:form>
        <p:panel header="Добро пожаловать!" style="max-width:400px; margin:auto; margin-top:50px;">
            <h:outputText value="Здравствуйте, #{sessionScope.username}!" style="font-size:18px; display:block; margin-bottom:10px;" />
            <h:outputText value="Вы успешно вошли в систему." />
        </p:panel>

        <div style="text-align:center; margin-top:20px;">
            <h:link outcome="index" value="Выйти" />
        </div>
    </h:form>
</h:body>
</html>
