package acc.br.login_service.model.dtos;

import acc.br.login_service.model.enums.UserRoles;

public record RegisterDTO(String login, String password, UserRoles role) {



}

