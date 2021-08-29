package com.github.janhenke.openandnetstat;

import java.io.UncheckedIOException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

	private final List<NetworkInterface> networkInterfaces = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		loadNetworkInterfaces();

		final RecyclerView recyclerView = findViewById(R.id.netif_list);

		final NetIfAdapter netIfAdapter = new NetIfAdapter();
		netIfAdapter.submitList(networkInterfaces);
		recyclerView.setAdapter(netIfAdapter);

		recyclerView.setLayoutManager(new LinearLayoutManager(this));

		recyclerView.setHasFixedSize(true);
		RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
		recyclerView.addItemDecoration(itemDecoration);
	}

	private void loadNetworkInterfaces() {
		final Enumeration<NetworkInterface> networkInterfaces;
		try {
			networkInterfaces = NetworkInterface.getNetworkInterfaces();
		} catch (final SocketException e) {
			Log.wtf("NetIFLoad failed", e);
			throw new UncheckedIOException(e);
		}

		while (networkInterfaces.hasMoreElements()) {
			this.networkInterfaces.add(networkInterfaces.nextElement());
		}
	}
}
