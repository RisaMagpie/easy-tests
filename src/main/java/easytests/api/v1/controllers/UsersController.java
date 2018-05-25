package easytests.api.v1.controllers;

import easytests.api.v1.exceptions.BadRequestException;
import easytests.api.v1.exceptions.ForbiddenException;
import easytests.api.v1.exceptions.NotFoundException;
import easytests.api.v1.exceptions.UnidentifiedModelException;
import easytests.api.v1.mappers.UsersMapper;
import easytests.api.v1.models.User;
import easytests.auth.services.SessionServiceInterface;
import easytests.core.models.UserModelInterface;
import easytests.core.options.UsersOptionsInterface;
import easytests.core.options.builder.UsersOptionsBuilderInterface;
import easytests.core.services.UsersServiceInterface;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;


/**
 * @author SvetlanaTselikova
 */
@RestController("UsersControllerV1")
@SuppressWarnings("checkstyle:MultipleStringLiterals")
@RequestMapping("/v1/users")
public class UsersController {

    @Autowired
    protected UsersServiceInterface usersService;

    @Autowired
    private UsersOptionsBuilderInterface usersOptionsBuilder;

    @Autowired
    private SessionServiceInterface sessionService;

    @Autowired
    @Qualifier("UsersMapperV1")
    private UsersMapper usersMapper;

    private Boolean isAdmin() {
        return this.sessionService.getUserModel().getIsAdmin();
    }

    @GetMapping("")
    public List<User> list() throws ForbiddenException {
        if (!this.isAdmin()) {
            throw new ForbiddenException();
        }
        final List<UserModelInterface> usersModels = this.usersService.findAll();

        return usersModels
                .stream()
                .map(model -> this.usersMapper.map(model, User.class))
                .collect(Collectors.toList());
    }
    /**
     * create
     */
    /**
     * update
     */

    @PutMapping("")
    public void update(@RequestBody User user) throws BadRequestException, NotFoundException {
        if (user.getId() == null) {
            throw new UnidentifiedModelException();
        }

        final UserModelInterface userModel = this.getUserModel(user.getId());

        this.usersMapper.map(user, userModel);

        this.usersService.save(userModel);
    }
    /**
     * show(userId)
     */
    /**
     * delete(userId)
     */
    /**
     * showMe
     */

    private UserModelInterface getUserModel(Integer id, UsersOptionsInterface userOptions) throws NotFoundException {
        final UserModelInterface userModel = this.usersService.find(id, userOptions);
        if (userModel == null) {
            throw new NotFoundException();
        }
        return userModel;
    }

    private UserModelInterface getUserModel(Integer id) throws NotFoundException {
        return this.getUserModel(id, this.usersOptionsBuilder.forAuth());
    }
}
