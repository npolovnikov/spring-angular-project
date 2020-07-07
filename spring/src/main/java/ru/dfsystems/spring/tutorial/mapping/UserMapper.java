package ru.dfsystems.spring.tutorial.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.dfsystems.spring.tutorial.dto.user.UserDto;
import ru.dfsystems.spring.tutorial.dto.user.UserListDto;
import ru.dfsystems.spring.tutorial.generated.tables.pojos.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Project spring-angular-project
 * Created by End on июль, 2020
 */
@Mapper
public interface UserMapper extends BaseMapper<User, UserDto, UserListDto> {

    UserListDto userToUserListDto(User user);

    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto userDto);

    default List<UserListDto> userListToUserListDto(List<User> users) {
        List<UserListDto> usersDto = new ArrayList<>();

        for (User user : users) {
            usersDto.add(userToUserListDto(user));
        }
        return usersDto;
    }
}
