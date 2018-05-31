package easytests.api.v1.mappers;

import easytests.api.v1.models.Issue;
import easytests.core.models.IssueModel;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


/**
 * @author Yarik2308
 */
@Service("IssuesMapperV1")
public class IssuesMapper extends ModelMapper {
    public IssuesMapper() {
        super();
        this.createTypeMap(IssueModel.class, Issue.class)
            .addMappings(
                mapper -> mapper
                    .<Integer>map(issueModel -> issueModel.getSubject().getId(),
                        (issue, id) -> issue.getSubject().setId(id))
            );
    }
}
