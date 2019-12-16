package designpatterns;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DurationHandler implements Handler {
        @PostConstruct
        public void init(){
            System.out.println(33333333333333333l);
        }

        @Override
        public void filterTask(HandlerContext ctx, Task task) {
            System.out.println("时效性检验");
            ctx.fireTaskFiltered(task);
        }
}
