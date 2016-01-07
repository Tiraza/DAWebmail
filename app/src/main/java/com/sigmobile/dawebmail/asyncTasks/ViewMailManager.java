package com.sigmobile.dawebmail.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;

import com.sigmobile.dawebmail.database.EmailMessage;
import com.sigmobile.dawebmail.database.User;
import com.sigmobile.dawebmail.scraper.ScrapingMachine;
import com.sigmobile.dawebmail.utils.Printer;

/**
 * Created by rish on 6/10/15.
 */
public class ViewMailManager extends AsyncTask<Void, Void, Void> {

    Context context;
    ViewMailListener viewMailListener;
    boolean result = false;
    String username, pwd;
    EmailMessage emailMessage;

    public ViewMailManager(Context context, ViewMailListener viewMailListener, EmailMessage emailMessage) {
        this.viewMailListener = viewMailListener;
        this.context = context;
        this.emailMessage = emailMessage;
        username = User.getUsername(context);
        pwd = User.getPassword(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        viewMailListener.onPreView();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        ScrapingMachine scrapper = new ScrapingMachine(username, pwd, context);
        result = scrapper.fetchEmailContent(emailMessage);

        Printer.println("IS USER LOGGED IN = " + User.isLoggedIn());
        Printer.println("Saved email now has content = " + emailMessage.content);

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        viewMailListener.onPostView(result);
    }
}