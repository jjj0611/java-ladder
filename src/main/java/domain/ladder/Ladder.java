package domain.ladder;

import controller.dto.LadderInputDto;
import domain.factory.RowFactory;
import domain.result.LadderResult;
import domain.result.Result;
import domain.user.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ladder {

    private List<User> users;
    private List<Row> rows = new ArrayList<>();
    private List<Result> results;

    public Ladder(LadderInputDto ladderInputDto, RowFactory rowFactory) {
        int numberOfSteps = ladderInputDto.getUserSize() - 1;
        for (int i = 0; i < ladderInputDto.getHeight(); i++) {
            rows.add(new Row(rowFactory.generateSteps(numberOfSteps)));
        }
        this.users = ladderInputDto.getUsers();
        this.results = ladderInputDto.getResults();
    }

    public int getResultFrom(int index) {
        for (Row row : rows) {
            index = row.getNextIndexFrom(index);
        }
        return index;
    }

    public Result getResultFrom(String userName) throws IllegalArgumentException {
        User targetUser = users.stream().filter(user -> user.getName().equals(userName)).findAny().orElseThrow(IllegalArgumentException::new);
        int targetIndex = users.indexOf(targetUser);
        int resultIndex = getResultFrom(targetIndex);
        return results.get(resultIndex);
    }

    public LadderResult getLadderResult() {
        Map<User, Result> ladderResult = new HashMap<>();

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            int resultIndex = getResultFrom(i);
            Result result = results.get(resultIndex);
            ladderResult.put(user, result);
        }

        return new LadderResult(ladderResult);
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Row> getRows() {
        return rows;
    }

    public List<Result> getResults() {
        return results;
    }
}
