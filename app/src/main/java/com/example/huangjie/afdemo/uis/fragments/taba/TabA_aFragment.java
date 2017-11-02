package com.example.huangjie.afdemo.uis.fragments.taba;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.huangjie.afdemo.R;
import com.example.huangjie.afdemo.entities.TabA_aEntity;
import com.example.huangjie.afdemo.uis.activities.tab_a_customerview.BezierActivity;
import com.example.huangjie.afdemo.uis.activities.tab_a_customerview.TopbarViewActivity;
import com.example.huangjie.afdemo.uis.activities.tab_a_customerview.TouchPullViewActivity;
import com.example.huangjie.afdemo.uis.activities.tab_a_customerview.widget.BezierView123;
import com.example.huangjie.afdemo.uis.activities.tab_a_customerview.widget.TouchPullView;
import com.example.huangjie.afdemo.uis.adapters.BaseAdapterWithHF;
import com.example.huangjie.afdemo.uis.adapters.TabA_aAdapter;
import com.flyco.tablayout.SlidingTabLayout;

import net.arvin.afbaselibrary.uis.fragments.BaseFragment;
import net.arvin.afbaselibrary.uis.fragments.BaseHeaderFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class TabA_aFragment extends BaseFragment implements BaseAdapterWithHF.OnItemClickListener<TabA_aEntity> {
    @BindView(R.id.rv_tab_a_a)
    RecyclerView rv_tab_a_a;

    private TabA_aAdapter mAdapter;

    @Override
    public int getContentView() {
        return R.layout.fragment_tab_a_a;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        List<TabA_aEntity> tabA_aEntities = new ArrayList<>();
        tabA_aEntities.add(new TabA_aEntity("TopBarView"));
        tabA_aEntities.add(new TabA_aEntity("TouchPullView"));
        tabA_aEntities.add(new TabA_aEntity("BezeierView123"));
        mAdapter = new TabA_aAdapter(getAFContext());
        mAdapter.setOnItemClickListener(this);
        rv_tab_a_a.setAdapter(mAdapter);
        rv_tab_a_a.setLayoutManager(new LinearLayoutManager(getAFContext()));
        mAdapter.refreshDatas(tabA_aEntities);
    }

    @Override
    public void onItemClick(View view, int position, TabA_aEntity tabA_aEntity) {
        switch (position) {
            case 0://TopBarView
                startActivity(TopbarViewActivity.class);
                break;
            case 1://TouchPullView
                startActivity(TouchPullViewActivity.class);
                break;
            case 2://BezeierView123
                startActivity(BezierActivity.class);
                break;
        }
    }
}
