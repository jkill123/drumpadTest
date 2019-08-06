package com.service;

import com.domain.Message;
import com.google.gson.Gson;
import com.model.IdListREsponse;
import com.model.ObjectListResponse;
import com.repos.MessageRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SendServiceImpl implements SendService{
    private MessageRepo messageRepo;

    public SendServiceImpl(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @Override
    public String getObjects(int[] result) {
        ObjectListResponse objectListResponse = new ObjectListResponse();
        ArrayList<Message> tmp = new ArrayList<>();
        try{
            for(int i=0;i<result.length;i++){
                ArrayList<Message> message = messageRepo.findAllById(result[i]);
                if(message.size()>=1){
                    objectListResponse.setResult(true);
                    tmp.add(message.get(0));
                }else {
                    tmp.add(null);
                }

            }
            objectListResponse.setObjects(tmp);
        }catch (Exception e){
            objectListResponse.setResult(false);
            System.out.println("Error getting list of objects");
        }
        Gson gson = new Gson();

        String resultGs = gson.toJson(objectListResponse);

        return resultGs;
    }

    @Override
    public String getIds() {
        IdListREsponse idListREsponse = new IdListREsponse();

        try{
            List<Message> list = messageRepo.findAll();
            List<Integer> idList = new ArrayList<>();
            for (Message value : list) {
                idList.add(value.getId());
            }
            idListREsponse.setResult(true);
            idListREsponse.setId(idList);
        }catch (Exception e ){
            idListREsponse.setResult(false);
        }
        Gson gson = new Gson();

        String resultGs = gson.toJson(idListREsponse);

        return resultGs;
    }
}
