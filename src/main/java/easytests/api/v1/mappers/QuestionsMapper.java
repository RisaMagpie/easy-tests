package easytests.api.v1.mappers;

import easytests.api.v1.models.Question;
import easytests.core.models.QuestionModel;
import easytests.core.models.empty.ModelsListEmpty;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


/**
 * @author RisaMagpie
 */
@Service("QuestionsMapperV1")
public class QuestionsMapper extends ModelMapper {
    public QuestionsMapper() {
        super();
        this.createTypeMap(QuestionModel.class, Question.class)
                .addMappings(mapper -> {
                            mapper.<Integer>map(questionModel -> questionModel.getTopic().getId(),
                                    (question, integer) -> question.getTopic().setId(integer));
                        }
                );
    }
}
