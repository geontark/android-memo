package com.example.memo.viewmodels;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.example.memo.R;
import com.example.memo.eventbus.EventBus;
import com.example.memo.eventbus.EventBusCode;
import com.example.memo.listener.LifecycleListener;
import com.example.memo.provider.ResourceProvider;
import com.example.memo.usecase.Usecase;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.rxjava3.functions.Consumer;


public class ToolbarViewModel {
    public ObservableField<Boolean> visibleModifyBtn;
    public ObservableField<Boolean> visibleDoneBtn;
    public ObservableField<Boolean> visibleDeleteBtn;

    public ObservableField<String> title;

    private LifecycleListener mLifecycleListener;
    private Usecase mUseCase;
    private ResourceProvider mResourceProvider;

    public ToolbarViewModel(ResourceProvider resProvier, Usecase usecase) {
        mUseCase = usecase;
        mResourceProvider = resProvier;
        init();
    }

    private void init() {
        visibleModifyBtn = new ObservableField<>(false);
        visibleDoneBtn = new ObservableField<>(false);
        visibleDeleteBtn = new ObservableField<>(false);
        title = new ObservableField<>("");
    }

    //    완료 버튼
    public void doneBtn() {
        JSONObject json = new JSONObject();
        int code = EventBusCode.TOOL_BAR_DONE;
        try {
            json.put(EventBusCode.CODE, code);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        EventBus.getInstance().sendEvent(json);
    }

    //수정 모드 변경 버튼
    public void modifyBtn() {
        visibleModifyBtn.set(false);
        visibleDeleteBtn.set(true);
        visibleDoneBtn.set(true);

        JSONObject json = new JSONObject();
        int code = EventBusCode.TOOL_BAR_MODIFY;
        try {

            json.put(EventBusCode.CODE, code);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        EventBus.getInstance().sendEvent(json);
    }

    //    삭제 버튼
    public void deleteBtn() {
        mUseCase.callAlertDialog(mResourceProvider.getString(R.string.alert_memo_delete))
                .subscribe(new Consumer() {
                    @Override
                    public void accept(Object o) throws Throwable {
                        Boolean isOk = (Boolean) o;
                        if (isOk) {
                            JSONObject json = new JSONObject();
                            int code = EventBusCode.TOOL_BAR_DELETE;
                            try {
                                json.put(EventBusCode.CODE, code);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            EventBus.getInstance().sendEvent(json);
                        }
                    }
                });
    }


    public void setVisibleModifyBtn(boolean visibleModifyBtn) {
        this.visibleModifyBtn.set(visibleModifyBtn);
    }

    public void setVisibleDoneBtn(boolean visibleDoneBtn) {
        this.visibleDoneBtn.set(visibleDoneBtn);
    }

    public void setTitle(String title) {
        this.title.set(title);
    }
}
